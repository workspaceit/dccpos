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
    public double getTotalSellingPrice(Collection<SaleDetails> saleDetails){
        return saleDetails.stream().mapToDouble(value -> value.getTotalPrice()).sum();
    }

    public int getTotalQuantity(Collection<SaleDetails> saleDetails){
        return saleDetails.stream().mapToInt(value -> value.getQuantity()).sum();
    }
}
