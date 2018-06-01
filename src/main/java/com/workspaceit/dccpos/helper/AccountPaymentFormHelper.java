package com.workspaceit.dccpos.helper;

import com.workspaceit.dccpos.validation.form.accounting.PaymentLedgerForm;

public class AccountPaymentFormHelper {
    public static double getTotalPaidAmount(PaymentLedgerForm[] accountPaymentForms){
        double totalPaidAmount = 0;
        if(accountPaymentForms==null)return totalPaidAmount;

        for(int i=0;i<accountPaymentForms.length;i++){
            PaymentLedgerForm accountPaymentForm =  accountPaymentForms[i];
            totalPaidAmount+=accountPaymentForm.getAmount();
        }
        return totalPaidAmount;
    }
}
