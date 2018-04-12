package com.workspaceit.pos.validation.form.personalIformation;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class PersonalInfoForm {

    @NotNull(message = "Full name required")
    @Length(max = 50,message = "Value too large")
    private String fullName;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dob;

    @Length(max=100,message = "Value is too large")
    private String email;

    @Length(max=30,message = "Value is too large")
    private String phone;

    @Length(max=200,message = "Value is too large")
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
