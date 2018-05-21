package com.workspaceit.dccpos.util.purchase;

import com.workspaceit.dccpos.validation.form.inventory.InventoryCreateFrom;
import org.springframework.stereotype.Component;

@Component
public class InventoryFormUtil {

    public double getTotalProductPrice(InventoryCreateFrom[] inventoryCreateFroms){
        double totalProductPrice = 0;

        if(inventoryCreateFroms==null)return totalProductPrice;

        for(InventoryCreateFrom inventoryCreateFrom:inventoryCreateFroms){
           double purchasePrice =  inventoryCreateFrom.getPurchasePrice()!=null?inventoryCreateFrom.getPurchasePrice():0;
           double purchaseQuantity =  inventoryCreateFrom.getPurchaseQuantity()!=null?inventoryCreateFrom.getPurchaseQuantity():0;
           totalProductPrice +=purchasePrice*purchaseQuantity;
        }

        return totalProductPrice;

    }

}
