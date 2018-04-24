package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.ValidationHelper;
import com.workspaceit.dccpos.service.accounting.LedgerService;
import com.workspaceit.dccpos.validation.form.purchase.AccountPaymentForm;
import com.workspaceit.dccpos.validation.form.purchase.PurchasePaymentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PurchasePaymentValidator {
    private LedgerService ledgerService;

    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    public void validate(String prefix,PurchasePaymentForm purchasePaymentForm, Errors errors){
       AccountPaymentForm[] accountPaymentForms =  purchasePaymentForm.getProductPricePaymentAccount();
        prefix =  ValidationHelper.preparePrefix(prefix);

        if(accountPaymentForms==null || accountPaymentForms.length==0){
            return;
        }
        this.validateAccountPayment(prefix+"paymentAccount",accountPaymentForms,errors);

    }

    public void validateAccountPayment(String prefix,AccountPaymentForm[] accountPaymentForms, Errors errors){
        for(int i=0;i<accountPaymentForms.length;i++){
            AccountPaymentForm accountPaymentForm =  accountPaymentForms[i];
            String ledgerFieldName = ValidationHelper.preparePrefix(prefix+"["+i+"]")+"ledgerId";
            String amountFieldName = ValidationHelper.preparePrefix(prefix+"["+i+"]")+"amount";

            Integer ledgerId = accountPaymentForm.getLedgerId();
            double amount = accountPaymentForm.getAmount();

            Ledger ledger = null;
            if(!errors.hasFieldErrors(ledgerFieldName)){
                try {
                    ledger = this.ledgerService.getLedger(ledgerId);
                } catch (EntityNotFound entityNotFound) {
                    errors.rejectValue(ledgerFieldName,entityNotFound.getMessage());
                    continue;
                }
            }

            if(!errors.hasFieldErrors(amountFieldName)){
                boolean isCashOrBankAccount = this.ledgerService.isCashOrBankAccount(ledger);
                boolean overDrawnFlag = this.ledgerService.isCashOrBankAccountWillOverDrawn(ledger,amount,false);

                if(!isCashOrBankAccount){
                    errors.rejectValue(amountFieldName,"Ledger Account is not bank or cash account");
                    continue;
                }
                if(overDrawnFlag){
                    errors.rejectValue(amountFieldName,"Account don't have sufficient balance");
                }
            }

        }
    }

}
