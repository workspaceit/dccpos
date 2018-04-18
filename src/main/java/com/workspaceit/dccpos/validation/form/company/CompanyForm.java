package com.workspaceit.dccpos.validation.form.company;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class CompanyForm {

    @NotEmpty(message = "Title name required")
    @Length(max = 50,message = "Value too large")
    private String title;

    @Length(max=100,message = "Value is too large")
    private String email;

    @Length(max=30,message = "Value is too large")
    private String phone;

    @Length(max=200,message = "Value is too large")
    private String address;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
