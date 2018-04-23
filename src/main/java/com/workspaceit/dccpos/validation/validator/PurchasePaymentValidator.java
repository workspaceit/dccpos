package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.service.accounting.LedgerService;
import com.workspaceit.dccpos.validation.form.purchase.AccountPaymentForm;
import com.workspaceit.dccpos.validation.form.purchase.PurchasePaymentForm;
import org.springframework.validation.Errors;

public class PurchasePaymentValidator {
    private LedgerService ledgerService;


    public void validate(PurchasePaymentForm purchasePaymentForm, Errors errors){
       AccountPaymentForm[] accountPaymentForms =  purchasePaymentForm.getPaymentAccount();


        if(accountPaymentForms==null || accountPaymentForms.length==0){
            return;
        }

        for(int i=0;i<accountPaymentForms.length;i++){
            AccountPaymentForm accountPaymentForm =  accountPaymentForms[i];


            Integer ledgerId = accountPaymentForm.getLedgerId();
            accountPaymentForm.getAmount();
        }

    }
}
