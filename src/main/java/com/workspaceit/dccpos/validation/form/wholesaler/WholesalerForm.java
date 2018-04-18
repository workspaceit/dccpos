package com.workspaceit.dccpos.validation.form.wholesaler;

import org.hibernate.validator.constraints.Length;


public class WholesalerForm {
    @Length(max = 50,message = "Value too large")
    protected String wholesalerId;

    public String getWholesalerId() {
        return wholesalerId;
    }

    public void setWholesalerId(String wholesalerId) {
        this.wholesalerId = wholesalerId;
    }
}