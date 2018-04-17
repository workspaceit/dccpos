package com.workspaceit.pos.validation.form.product;

import com.workspaceit.pos.constant.WEIGHT_UNIT;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

public class ProductForm {
    private Integer categoryId;

    @NotEmpty(message = "Product name required")
    @Length(max = 200,message = "Value too large")
    private String name;

    @Min(value = 0,message = "Weight Can't less be zero")
    private Integer weight;

    private WEIGHT_UNIT weightUnit;

    @Length(max = 50,message = "Value too large")
    private String barcode;

    private Integer imageToken;



    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public WEIGHT_UNIT getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(WEIGHT_UNIT weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getImageToken() {
        return imageToken;
    }

    public void setImageToken(Integer imageToken) {
        this.imageToken = imageToken;
    }
}