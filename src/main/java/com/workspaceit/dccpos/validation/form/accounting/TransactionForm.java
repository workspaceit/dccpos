package com.workspaceit.dccpos.validation.form.accounting;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TransactionForm {
    @Valid
    @NotNull(message = "Select a beneficial account")
    private PaymentLedgerForm beneficial;

    @Valid
    @NotNull(message = "Select a bank or cash account")
    private PaymentLedgerForm[] cashOrBank;

    private String narration;

    @NotNull(message = "Date required")
    private Date date;

    public PaymentLedgerForm getBeneficial() {
        return beneficial;
    }

    public void setBeneficial(PaymentLedgerForm beneficial) {
        this.beneficial = beneficial;
    }

    public PaymentLedgerForm[] getCashOrBank() {
        return cashOrBank;
    }

    public void setCashOrBank(PaymentLedgerForm[] cashOrBank) {
        this.cashOrBank = cashOrBank;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}