package com.workspaceit.pos.validation.form;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PersonalInfoForm {

    private String fullName;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dob;

    private String email;
    private String phone;
    private String address;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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
