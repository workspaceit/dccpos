package com.workspaceit.dccpos.util;

import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import com.workspaceit.dccpos.entity.Inventory;
import com.workspaceit.dccpos.entity.SaleDetails;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.validation.form.sale.InventorySaleForm;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class SaleDetailsUtil {

    public double getTotalPurchasePrice(Collection<SaleDetails> saleDetails){
        return saleDetails.stream().mapToDouble(value -> {
            double price = value.getInventory().getPurchasePrice();
            int quantity = value.getQuantity();

            return price * quantity;
        }).sum();
    }
}
