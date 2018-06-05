package com.workspaceit.dccpos.dataModel;

public class InvoiceDetails {
    private String productName;
    private int quantity;
    private double perQuantityPrice;
    private double totalPrice;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPerQuantityPrice() {
        return perQuantityPrice;
    }

    public void setPerQuantityPrice(double perQuantityPrice) {
        this.perQuantityPrice = perQuantityPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}