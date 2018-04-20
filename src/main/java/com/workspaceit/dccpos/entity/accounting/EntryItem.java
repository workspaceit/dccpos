package com.workspaceit.dccpos.entity.accounting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.entity.Employee;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "acc_entry_items")
public class EntryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn( name = "entry_id",referencedColumnName = "id")
    private Entry entry;

    @ManyToOne
    @JoinColumn(name = "ledger_id",referencedColumnName = "id")
    private Ledger ledger;

    @Column(name = "amount")
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "dc")
    private ACCOUNTING_ENTRY accountingEntry;

    @Column(name = "reconciliation_date")
    private Date reconciliationDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date   createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by",referencedColumnName = "id")
    private Employee createdBy;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Ledger getLedger() {
        return ledger;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ACCOUNTING_ENTRY getAccountingEntry() {
        return accountingEntry;
    }

    public void setAccountingEntry(ACCOUNTING_ENTRY accountingEntry) {
        this.accountingEntry = accountingEntry;
    }

    public Date getReconciliationDate() {
        return reconciliationDate;
    }

    public void setReconciliationDate(Date reconciliationDate) {
        this.reconciliationDate = reconciliationDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
    }
}
