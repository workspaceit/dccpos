package com.workspaceit.pos.validation.form.accounting;

import com.workspaceit.pos.constant.accounting.ACCOUNTING_ENTRY;

public class LedgerForm {
    private String ledgerName;
    private ACCOUNTING_ENTRY accountingEntry;
    private Boolean bankOrCash;
    private String note;

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public ACCOUNTING_ENTRY getAccountingEntry() {
        return accountingEntry;
    }

    public void setAccountingEntry(ACCOUNTING_ENTRY accountingEntry) {
        this.accountingEntry = accountingEntry;
    }

    public Boolean isBankOrCash() {
        return bankOrCash;
    }

    public void setBankOrCash(Boolean bankOrCash) {
        this.bankOrCash = bankOrCash;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}