package com.workspaceit.dccpos.validation.form.inventory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryFrom {

    @NotNull(message = "Product Id required")
    private Integer productId;

    @NotNull(message = "Purchase price required")
    @Min(value = 1,message = "Price can't be less then 1")
    private Double purchasePrice;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}