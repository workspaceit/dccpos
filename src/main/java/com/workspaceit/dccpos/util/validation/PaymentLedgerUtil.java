package com.workspaceit.dccpos.util.validation;

import com.workspaceit.dccpos.validation.form.accounting.PaymentLedgerForm;
import org.springframework.stereotype.Component;

@Component
public class PaymentLedgerUtil {
    public double sumAmount(PaymentLedgerForm[] ledgerEntryForms){
        double totalAmount = 0d;

        if(ledgerEntryForms==null)return totalAmount;
        for (PaymentLedgerForm ledgerEntryForm: ledgerEntryForms) {
            double amount = ledgerEntryForm.getAmount()!=null?ledgerEntryForm.getAmount():0;
            totalAmount += amount;
        }

        return totalAmount;
    }
}
