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
    private AccountPaymentForm[] paymentAccount;

    public Double getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(Double totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public AccountPaymentForm[] getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(AccountPaymentForm[] paymentAccount) {
        this.paymentAccount = paymentAccount;
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

    @Override
    public String toString() {
        return "PurchasePaymentForm{" +
                "totalPaidAmount=" + totalPaidAmount +
                ", paymentAccount=" + Arrays.toString(paymentAccount) +
                '}';
    }
}