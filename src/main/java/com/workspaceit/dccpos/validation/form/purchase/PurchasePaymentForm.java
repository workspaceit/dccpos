package com.workspaceit.dccpos.validation.form.purchase;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PurchasePaymentForm {
    @NotNull
    @Min(value = 0,message = "Amount can't be less then zero")
    private Double totalPaidAmount;

    @Valid
    private AccountPaymentForm paymentAccount;

    public Double getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(Double totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public AccountPaymentForm getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(AccountPaymentForm paymentAccount) {
        this.paymentAccount = paymentAccount;
    }
}