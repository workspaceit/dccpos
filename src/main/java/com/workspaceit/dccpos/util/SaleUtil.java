package com.workspaceit.dccpos.util;

import com.workspaceit.dccpos.entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaleUtil {
    private SaleDetailsUtil saleDetailsUtil;

    @Autowired
    public void setSaleDetailsUtil(SaleDetailsUtil saleDetailsUtil) {
        this.saleDetailsUtil = saleDetailsUtil;
    }

    public double getTotalPrice(Sale sale){
        double vat = sale.getVat();
        double discount = sale.getDiscount();
        double totalSoldProductPrice = this.saleDetailsUtil.getTotalSellingPrice(sale.getSaleDetails());

        double totalPrice = (( totalSoldProductPrice + vat ) - discount );
        return totalPrice;
    }
}
