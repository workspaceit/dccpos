package com.workspaceit.dccpos.validation.form.accounting;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class InvestmentForm {
    @Valid
    @NotNull
    private LedgerEntryForm[] cashOrBank;

     private String narration;

    @NotNull(message = "Date required")
    private Date date;


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