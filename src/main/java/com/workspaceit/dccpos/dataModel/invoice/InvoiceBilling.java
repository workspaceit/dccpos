package com.workspaceit.dccpos.dataModel.invoice;

public class InvoiceBilling {
    private String id;
    private String name;
    private InvoiceBillingAddress address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InvoiceBillingAddress getAddress() {
        return address;
    }

    public void setAddress(InvoiceBillingAddress address) {
        this.address = address;
    }
}
