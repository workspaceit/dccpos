package com.workspaceit.dccpos.validation.form.shop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopForm {

    @NotNull(message = "Name Required")
    @Length(max = 100,message = "Value too large")
    private String name;

    @Length(max = 100,message = "Value too large")
    private String address;


    @Pattern(regexp ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$", message="Email is not valid")
    private String email;

    @NotNull(message = "Phone Required")
    private String phone;

    @NotNull(message = "Image Token Required")
    private Integer imageToken;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {this.phone = phone;}

    public Integer getImageToken() {
        return imageToken;
    }

    public void setImageToken(Integer imageToken) {
        this.imageToken = imageToken;
    }
   /* @Override
    public String toString() {
        return "ShopForm{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", email=" + email +
                ", logo=" + logo +
                ", phone=" + phone +
                ", imageToken=" + imageToken +
                '}';
    }*/
}
