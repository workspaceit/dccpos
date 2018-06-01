package com.workspaceit.dccpos.util;

import com.workspaceit.dccpos.entity.SaleDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

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
