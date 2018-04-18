package com.workspaceit.dccpos.validation.form.supplier;

import org.hibernate.validator.constraints.Length;


public class SupplierForm {
    @Length(max = 50,message = "Value too large")
    protected String supplierId;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}