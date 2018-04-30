package com.workspaceit.dccpos.helper;

import com.workspaceit.dccpos.validation.form.accounting.LedgerEntryForm;

public class AccountPaymentFormHelper {
    public static double getTotalPaidAmount(LedgerEntryForm[] accountPaymentForms){
        double totalPaidAmount = 0;
        if(accountPaymentForms==null)return totalPaidAmount;

        for(int i=0;i<accountPaymentForms.length;i++){
            LedgerEntryForm accountPaymentForm =  accountPaymentForms[i];
            totalPaidAmount+=accountPaymentForm.getAmount();
        }
        return totalPaidAmount;
    }
}
