package com.workspaceit.pos.entity.accounting;

import com.workspaceit.pos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.pos.constant.accounting.LEDGER_TYPE;
import com.workspaceit.pos.entity.PersonalInformation;
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
    @JoinColumn(name = "group_id",referencedColumnName = "id",nullable = false)
    private GroupAccount groupAccount;

    @ManyToOne
    @JoinColumn(name = "personal_info_id",referencedColumnName = "id")
    private PersonalInformation personalInformation;

    @Column(name = "name")
    private String name;


    @Column(name =  "op_balance")
    private Double openingBalance;

    @Enumerated(EnumType.STRING)
    @Column(name =  "op_balance_dc")
    private ACCOUNTING_ENTRY openingEntryType;

    @Enumerated(EnumType.STRING)
    @Column(name =  "type")
    private LEDGER_TYPE ledgerType;

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

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ACCOUNTING_ENTRY getOpeningEntryType() {
        return openingEntryType;
    }

    public void setOpeningEntryType(ACCOUNTING_ENTRY openingEntryType) {
        this.openingEntryType = openingEntryType;
    }

    public LEDGER_TYPE getLedgerType() {
        return ledgerType;
    }

    public void setLedgerType(LEDGER_TYPE ledgerType) {
        this.ledgerType = ledgerType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ledger ledger = (Ledger) o;

        if (id != ledger.id) return false;
        if (reconciliation != ledger.reconciliation) return false;
        if (groupAccount != null ? !groupAccount.equals(ledger.groupAccount) : ledger.groupAccount != null)
            return false;
        if (personalInformation != null ? !personalInformation.equals(ledger.personalInformation) : ledger.personalInformation != null)
            return false;
        if (name != null ? !name.equals(ledger.name) : ledger.name != null) return false;
        if (openingBalance != null ? !openingBalance.equals(ledger.openingBalance) : ledger.openingBalance != null)
            return false;
        if (openingEntryType != ledger.openingEntryType) return false;
        if (ledgerType != ledger.ledgerType) return false;
        if (notes != null ? !notes.equals(ledger.notes) : ledger.notes != null) return false;
        return createdAt != null ? createdAt.equals(ledger.createdAt) : ledger.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (groupAccount != null ? groupAccount.hashCode() : 0);
        result = 31 * result + (personalInformation != null ? personalInformation.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (openingBalance != null ? openingBalance.hashCode() : 0);
        result = 31 * result + (openingEntryType != null ? openingEntryType.hashCode() : 0);
        result = 31 * result + (ledgerType != null ? ledgerType.hashCode() : 0);
        result = 31 * result + reconciliation;
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}