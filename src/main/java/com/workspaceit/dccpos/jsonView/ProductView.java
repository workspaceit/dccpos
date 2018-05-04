package com.workspaceit.dccpos.jsonView;

public class ProductView {
    public interface Basic{}
    public interface Summary extends Basic{}
    public interface Details extends Summary,InventoryView.Summary
    {}
}
