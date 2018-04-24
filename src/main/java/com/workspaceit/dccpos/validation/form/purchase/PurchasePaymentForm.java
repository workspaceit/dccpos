package com.workspaceit.dccpos.validation.form.purchase;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class PurchasePaymentForm {
    @NotNull
    @Min(value = 0,message = "Amount can't be less then zero")
    private Double totalPaidAmount;

    @NotNull
    @Min(value = 0,message = "Amount can't be less then zero")
    private Double paidCostAmount;

    @NotNull
    @Min(value = 0,message = "Amount can't be less then zero")
    private Double paidProductPriceAmount;

    @Valid
    private AccountPaymentForm[] productPricePaymentAccount;

    @Valid
    private AccountPaymentForm shippingCostPaymentAccount;


    public Double getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(Double totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public AccountPaymentForm[] getProductPricePaymentAccount() {
        return productPricePaymentAccount;
    }

    public void setProductPricePaymentAccount(AccountPaymentForm[] productPricePaymentAccount) {
        this.productPricePaymentAccount = productPricePaymentAccount;
    }


    public Double getPaidCostAmount() {
        return paidCostAmount;
    }

    public void setPaidCostAmount(Double paidCostAmount) {
        this.paidCostAmount = paidCostAmount;
    }

    public Double getPaidProductPriceAmount() {
        return paidProductPriceAmount;
    }

    public void setPaidProductPriceAmount(Double paidProductPriceAmount) {
        this.paidProductPriceAmount = paidProductPriceAmount;
    }

    public AccountPaymentForm getShippingCostPaymentAccount() {
        return shippingCostPaymentAccount;
    }

    public void setShippingCostPaymentAccount(AccountPaymentForm shippingCostPaymentAccount) {
        this.shippingCostPaymentAccount = shippingCostPaymentAccount;
    }

    @Override
    public String toString() {
        return "PurchasePaymentForm{" +
                "totalPaidAmount=" + totalPaidAmount +
                ", paidCostAmount=" + paidCostAmount +
                ", paidProductPriceAmount=" + paidProductPriceAmount +
                ", productPricePaymentAccount=" + Arrays.toString(productPricePaymentAccount) +
                ", shippingCostPaymentAccount=" + shippingCostPaymentAccount +
                '}';
    }
}