package com.workspaceit.pos.entity.accounting;

import com.workspaceit.pos.constant.accounting.ENTRY_TYPE;
import com.workspaceit.pos.constant.accounting.LEDGER_TYPE;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "acc_ledgers")
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "group_id",referencedColumnName = "id",insertable = false,updatable = false)
    private GroupAccount groupAccount;

    @Column(name = "name")
    private String name;

    @Column(name =  "code" )
    private String code;

    @Column(name =  "op_balance")
    private Double openingBalance;

    @Enumerated(EnumType.STRING)
    @Column(name =  "op_balance_dc")
    private ENTRY_TYPE openingEntryType;

    @Enumerated(EnumType.STRING)
    @Column(name =  "type")
    private LEDGER_TYPE accountType;

    @Column(name =  "reconciliation")
    private int reconciliation;

    @Column(name =  "notes")
    private String notes;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GroupAccount getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(GroupAccount groupAccount) {
        this.groupAccount = groupAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public ENTRY_TYPE getOpeningEntryType() {
        return openingEntryType;
    }

    public void setOpeningEntryType(ENTRY_TYPE openingEntryType) {
        this.openingEntryType = openingEntryType;
    }

    public LEDGER_TYPE getAccountType() {
        return accountType;
    }

    public void setAccountType(LEDGER_TYPE accountType) {
        this.accountType = accountType;
    }

    public int getReconciliation() {
        return reconciliation;
    }

    public void setReconciliation(int reconciliation) {
        this.reconciliation = reconciliation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}