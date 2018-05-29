package com.workspaceit.dccpos.validation.form.sale;

import com.workspaceit.dccpos.constant.SALE_TYPE;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoCreateForm;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SaleForm {

    @NotNull(message = "Product required")
    @Valid
    private InventorySaleForm[] inventories;
    private Double discount;

    @NotNull(message = "Paid amount required")
    @Min(value = 0,message = "Amount can't be less equal zero")
    private Double paidAmount;

    @NotNull(message = "Vat required")
    @Min(value = 0,message = "Amount can't be less equal zero")
    private Double vat;

    @NotNull(message = "Sale type required")
    private SALE_TYPE type;

    /**
     * Sale to
     * wholesaler or
     * new consumer or
     * existing consumer
     * */
    private Integer wholesalerId;

    @Valid
    private PersonalInfoCreateForm consumerInfo;
    private Integer consumerInfoId;

    public InventorySaleForm[] getInventories() {
        return inventories;
    }

    public void setInventories(InventorySaleForm[] inventories) {
        this.inventories = inventories;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public SALE_TYPE getType() {
        return type;
    }

    public void setType(SALE_TYPE type) {
        this.type = type;
    }

    public Integer getWholesalerId() {
        return wholesalerId;
    }

    public void setWholesalerId(Integer wholesalerId) {
        this.wholesalerId = wholesalerId;
    }

    public PersonalInfoCreateForm getConsumerInfo() {
        return consumerInfo;
    }

    public void setConsumerInfo(PersonalInfoCreateForm consumerInfo) {
        this.consumerInfo = consumerInfo;
    }

    public Integer getConsumerInfoId() {
        return consumerInfoId;
    }

    public void setConsumerInfoId(Integer consumerInfoId) {
        this.consumerInfoId = consumerInfoId;
    }
}
