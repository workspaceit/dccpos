package com.workspaceit.dccpos.util.validation;

import com.workspaceit.dccpos.validation.form.sale.SaleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaleFormUtil {
    private InventorySaleUtil inventorySaleValidatorUtil;

    @Autowired
    public void setInventorySaleValidatorUtil(InventorySaleUtil inventorySaleValidatorUtil) {
        this.inventorySaleValidatorUtil = inventorySaleValidatorUtil;
    }

    public double getAdditionalCost(SaleForm saleForm){
        double vat = saleForm.getVat()!=null?saleForm.getVat():0;
        return vat;
    }
    public double getTotalPayablePrice(SaleForm saleForm){
        double discount = saleForm.getDiscount()!=null?saleForm.getDiscount():0;
        double totalInventoryPrice = this.inventorySaleValidatorUtil.sumAmount(saleForm.getInventories());
        double totalPayablePrice = this.getAdditionalCost(saleForm)+totalInventoryPrice-discount;
        return totalPayablePrice;
    }
}
