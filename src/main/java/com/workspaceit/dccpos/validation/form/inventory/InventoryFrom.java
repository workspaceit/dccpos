package com.workspaceit.dccpos.validation.form.inventory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryFrom {

    @NotNull(message = "Product Id required")
    private Integer productId;

    @NotNull(message = "Purchase price required")
    @Min(value = 1,message = "Price can't be less then 1")
    private Double purchasePrice;

    @NotNull(message = "Purchase quantity required")
    @Min(value = 1,message = "Quantity can't be less then 1")
    private Integer purchaseQuantity;

    @NotNull(message = "Status required")
    private PRODUCT_CONDITION status;

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
    }

    @Override
    public String toString() {
        return "InventoryFrom{" +
                "productId=" + productId +
                ", purchasePrice=" + purchasePrice +
                ", purchaseQuantity=" + purchaseQuantity +
                ", status=" + status +
                '}';
    }
}