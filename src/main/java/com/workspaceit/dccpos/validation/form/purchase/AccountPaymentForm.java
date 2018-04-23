package com.workspaceit.dccpos.validation.form.purchase;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AccountPaymentForm {
    @NotNull(message = "Ledger Id required")
    @Min(value = 0,message = "Ledger Id required")
    private Integer ledgerId;

    @Min(value = 1,message = "Amount can't be less equal to zero")
    private Double amount;

    public Integer getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Integer ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
