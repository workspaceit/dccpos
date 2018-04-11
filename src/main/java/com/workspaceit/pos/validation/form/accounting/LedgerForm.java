package com.workspaceit.pos.validation.form.accounting;

import com.workspaceit.pos.constant.accounting.ACCOUNTING_ENTRY;

public class LedgerForm {
    private String ledgerName;
    private ACCOUNTING_ENTRY ledgerAccountingEntry;
    private Boolean ledgerBankOrCash;
    private String ledgerNote;

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public ACCOUNTING_ENTRY getLedgerAccountingEntry() {
        return ledgerAccountingEntry;
    }

    public void setLedgerAccountingEntry(ACCOUNTING_ENTRY ledgerAccountingEntry) {
        this.ledgerAccountingEntry = ledgerAccountingEntry;
    }

    public Boolean isBankOrCash() {
        return ledgerBankOrCash;
    }

    public void setLedgerBankOrCash(Boolean ledgerBankOrCash) {
        this.ledgerBankOrCash = ledgerBankOrCash;
    }

    public String getLedgerNote() {
        return ledgerNote;
    }

    public void setLedgerNote(String ledgerNote) {
        this.ledgerNote = ledgerNote;
    }
}