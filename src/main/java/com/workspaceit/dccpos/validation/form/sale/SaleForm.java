package com.workspaceit.dccpos.validation.form.sale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.workspaceit.dccpos.constant.SALE_TYPE;
import com.workspaceit.dccpos.validation.form.accounting.PaymentLedgerForm;
import com.workspaceit.dccpos.validation.form.personalIformation.PersonalInfoCreateForm;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
    public class SaleForm {

    @NotNull(message = "Product required")
    @Valid
    private InventorySaleForm[] inventories;

    /**
     * double For default value 0
     * */
    private Double discount;

    @NotNull(message = "Vat required")
    @Min(value = 0,message = "Amount can't be less equal zero")
    private Double vat;

    @NotNull(message = "Sale type required")
    private SALE_TYPE type;

    @NotNull
    private Date date;

    private String description;

    /**
     * Sale to
     * wholesaler or
     * new consumer or
     * existing consumer
     * */
    private Integer wholesalerId;

    @Valid
    private PaymentLedgerForm[] paymentAccount;

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


    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public SALE_TYPE getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(SALE_TYPE type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public PaymentLedgerForm[] getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(PaymentLedgerForm[] paymentAccount) {
        this.paymentAccount = paymentAccount;
    }
}
