package com.workspaceit.dccpos.validation.form.accounting;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TransactionForm {
    @Valid
    @NotNull
    private LedgerEntryForm beneficial;

    @Valid
    @NotNull
    private LedgerEntryForm[] cashOrBank;

    private String narration;

    @NotNull(message = "Date required")
    private Date date;

    public LedgerEntryForm getBeneficial() {
        return beneficial;
    }

    public void setBeneficial(LedgerEntryForm beneficial) {
        this.beneficial = beneficial;
    }

    public LedgerEntryForm[] getCashOrBank() {
        return cashOrBank;
    }

    public void setCashOrBank(LedgerEntryForm[] cashOrBank) {
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