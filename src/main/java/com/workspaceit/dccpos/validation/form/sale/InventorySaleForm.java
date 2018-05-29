package com.workspaceit.dccpos.validation.form.sale;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InventorySaleForm {
    @NotNull(message = "Inventory Id required")
    private Integer inventoryId;

    @NotNull(message = "Selling price required")
    @Min(value = 1,message = "Selling price can't be zero or negative")
    private Double sellingPrice;

    @NotNull(message = "Quantity required")
    @Min(value = 1,message = "Quantity can't be zero or negative")
    private Integer quantity;

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
