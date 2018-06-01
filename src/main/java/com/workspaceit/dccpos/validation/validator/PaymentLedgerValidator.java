package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.accounting.Ledger;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.ValidationHelper;
import com.workspaceit.dccpos.service.accounting.LedgerService;
import com.workspaceit.dccpos.validation.form.accounting.PaymentLedgerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PaymentLedgerValidator {
    private LedgerService ledgerService;



    @Autowired
    public void setLedgerService(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }


    public void validatePayment(String prefix, PaymentLedgerForm[] accountPaymentForms, Errors errors){
        if(accountPaymentForms==null || accountPaymentForms.length==0){
            return;
        }
        this.validateAccountPayment(prefix,accountPaymentForms,errors);

    }
    public void validateReceived(String prefix, PaymentLedgerForm[] accountPaymentForms, Errors errors){
        if(accountPaymentForms==null || accountPaymentForms.length==0){
            return;
        }
        this.validateAccountReceived(prefix,accountPaymentForms,errors);

    }
    public void validateAccountReceived(String prefix, PaymentLedgerForm[] accountPaymentForms, Errors errors){
        for(int i=0;i<accountPaymentForms.length;i++){
            PaymentLedgerForm accountPaymentForm =  accountPaymentForms[i];
            this.validateAccountReceived(prefix+"["+i+"]", accountPaymentForm, errors);
        }
    }
    public void validateAccountPayment(String prefix, PaymentLedgerForm[] accountPaymentForms, Errors errors){
        for(int i=0;i<accountPaymentForms.length;i++){
            PaymentLedgerForm accountPaymentForm =  accountPaymentForms[i];
            this.validateAccountPayment(prefix+"["+i+"]", accountPaymentForm, errors);
        }
    }
    public void validateAccountPayment(String prefix, PaymentLedgerForm accountPaymentForm, Errors errors){
            if(accountPaymentForm==null ){
                return;
            }

            String amountFieldName = ValidationHelper.preparePrefix(prefix)+"amount";

            Integer ledgerId = accountPaymentForm.getLedgerId();
            double amount = accountPaymentForm.getAmount();

            this.validateLedger(prefix,accountPaymentForm,errors);
            Ledger ledger = this.ledgerService.getById(ledgerId);

            if(ledger!=null && !errors.hasFieldErrors(amountFieldName)){
                boolean isCashOrBankAccount = this.ledgerService.isCashOrBankAccount(ledger);
                boolean overDrawnFlag = this.ledgerService.isCashOrBankAccountWillOverDrawn(ledger,amount,false);

                if(!isCashOrBankAccount){
                    errors.rejectValue(amountFieldName,"Ledger Account is not bank or cash account");
                    return;
                }
                if(overDrawnFlag){
                    errors.rejectValue(amountFieldName,"Account don't have sufficient balance");
                }
            }


    }
    public void validateLedger(String prefix, PaymentLedgerForm accountPaymentForm, Errors errors){

        String ledgerFieldName = ValidationHelper.preparePrefix(prefix)+"ledgerId";
        Integer ledgerId = accountPaymentForm.getLedgerId();

        if(!errors.hasFieldErrors(ledgerFieldName)){
            try {
               Ledger ledger = this.ledgerService.getLedger(ledgerId);
            } catch (EntityNotFound entityNotFound) {
                errors.rejectValue(ledgerFieldName,entityNotFound.getMessage());
                return;
            }
        }
    }
    public void validateAccountReceived(String prefix, PaymentLedgerForm accountPaymentForm, Errors errors){
        if(accountPaymentForm==null ){
            return;
        }

        String amountFieldName = ValidationHelper.preparePrefix(prefix)+"amount";
        Integer ledgerId = accountPaymentForm.getLedgerId();

        Ledger ledger = this.ledgerService.getById(ledgerId);

        if(!errors.hasFieldErrors(amountFieldName)){
            boolean isCashOrBankAccount = this.ledgerService.isCashOrBankAccount(ledger);
            if(!isCashOrBankAccount){
                errors.rejectValue(amountFieldName,"Ledger Account is not bank or cash account");
                return;
            }

        }


    }

}
