package com.workspaceit.dccpos.helper;

import com.workspaceit.dccpos.validation.form.purchase.AccountPaymentForm;

public class AccountPaymentFormHelper {
    public static double getTotalPaidAmount(AccountPaymentForm[] accountPaymentForms){
        double totalPaidAmount = 0;
        if(accountPaymentForms==null)return totalPaidAmount;

        for(int i=0;i<accountPaymentForms.length;i++){
            AccountPaymentForm accountPaymentForm =  accountPaymentForms[i];
            totalPaidAmount+=accountPaymentForm.getAmount();
        }
        return totalPaidAmount;
    }
}
