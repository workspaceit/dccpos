package com.workspaceit.dccpos.util.validation;

import com.workspaceit.dccpos.validation.form.sale.InventorySaleForm;
import org.springframework.stereotype.Component;

@Component
public class InventorySaleUtil {
    public double sumAmount(InventorySaleForm[] ledgerEntryForms){
        double totalAmount = 0d;

        if(ledgerEntryForms==null)return totalAmount;

        for (InventorySaleForm inventorySaleForm: ledgerEntryForms) {
            double amount = inventorySaleForm.getSellingPrice()!=null?inventorySaleForm.getSellingPrice():0;
            double quantity = inventorySaleForm.getQuantity()!=null?inventorySaleForm.getQuantity():0;

            totalAmount += amount*quantity;
        }

        return totalAmount;
    }
}
