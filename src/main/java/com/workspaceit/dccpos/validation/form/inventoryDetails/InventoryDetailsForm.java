package com.workspaceit.dccpos.validation.form.inventoryDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryDetailsForm {
   /* @NotNull(message = "Selling price required")
    @Min(value = 1,message = "Selling can't be less then 1")
    private Double sellingPrice;

    @NotNull(message = "Purchase quantity required")
    @Min(value = 1,message = "Quantity can't be less then 1")
    private Integer purchaseQuantity;


    @NotNull(message = "Status required")
    private PRODUCT_CONDITION status;

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Integer purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public PRODUCT_CONDITION getStatus() {
        return status;
    }

    public void setStatus(PRODUCT_CONDITION status) {
        this.status = status;
    }*/
}
