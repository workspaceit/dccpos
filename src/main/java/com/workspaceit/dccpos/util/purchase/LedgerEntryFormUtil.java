package com.workspaceit.dccpos.util.purchase;

import com.workspaceit.dccpos.validation.form.accounting.LedgerEntryForm;
import org.springframework.stereotype.Component;

@Component
public class LedgerEntryFormUtil {
    public double getTotalAmount(LedgerEntryForm[] ledgerEntryForms){
        double totalAmount = 0d;

        if(ledgerEntryForms==null)return totalAmount;
        for (LedgerEntryForm ledgerEntryForm: ledgerEntryForms) {
            double amount = ledgerEntryForm.getAmount()!=null?ledgerEntryForm.getAmount():0;
            totalAmount += amount;
        }

        return totalAmount;
    }
}
