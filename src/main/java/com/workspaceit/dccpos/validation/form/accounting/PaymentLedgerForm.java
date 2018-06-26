package com.workspaceit.dccpos.validation.form.accounting;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PaymentLedgerForm {
    @NotNull(message = "Ledger required")
    @Min(value = 1,message = "Ledger required")
    private Integer ledgerId;

    @NotNull(message = "Payment amount required")
    @Min(value = 1,message = "Payment amount can't be less equal to zero")
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

    @Override
    public String toString() {
        return "AccountPaymentForm{" +
                "ledgerId=" + ledgerId +
                ", amount=" + amount +
                '}';
    }


}
