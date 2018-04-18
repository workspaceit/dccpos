package com.workspaceit.dccpos.entity.accounting;

import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.constant.accounting.LEDGER_TYPE;
import com.workspaceit.dccpos.entity.Company;
import com.workspaceit.dccpos.entity.PersonalInformation;
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

    @ManyToOne
    @JoinColumn(name = "company_id",referencedColumnName = "id")
    private Company company;

    @Column(name = "name")
    private String name;


    @Column(name =  "op_balance")
    private Double openingBalance;

    @Enumerated(EnumType.STRING)
    @Column(name =  "op_balance_dc")
    private ACCOUNTING_ENTRY openingBalanceEntryType;

    @Column(name =  "current_balance")
    private Double currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name =  "current_balance_dc")
    private ACCOUNTING_ENTRY currentBalanceEntryType;

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public ACCOUNTING_ENTRY getCurrentBalanceEntryType() {
        return currentBalanceEntryType;
    }

    public void setCurrentBalanceEntryType(ACCOUNTING_ENTRY currentBalanceEntryType) {
        this.currentBalanceEntryType = currentBalanceEntryType;
    }

    public ACCOUNTING_ENTRY getOpeningBalanceEntryType() {
        return openingBalanceEntryType;
    }

    public void setOpeningBalanceEntryType(ACCOUNTING_ENTRY openingBalanceEntryType) {
        this.openingBalanceEntryType = openingBalanceEntryType;
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
        if (company != null ? !company.equals(ledger.company) : ledger.company != null) return false;
        if (name != null ? !name.equals(ledger.name) : ledger.name != null) return false;
        if (openingBalance != null ? !openingBalance.equals(ledger.openingBalance) : ledger.openingBalance != null)
            return false;
        if (openingBalanceEntryType != ledger.openingBalanceEntryType) return false;
        if (currentBalance != null ? !currentBalance.equals(ledger.currentBalance) : ledger.currentBalance != null)
            return false;
        if (currentBalanceEntryType != ledger.currentBalanceEntryType) return false;
        if (ledgerType != ledger.ledgerType) return false;
        if (notes != null ? !notes.equals(ledger.notes) : ledger.notes != null) return false;
        return createdAt != null ? createdAt.equals(ledger.createdAt) : ledger.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (groupAccount != null ? groupAccount.hashCode() : 0);
        result = 31 * result + (personalInformation != null ? personalInformation.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (openingBalance != null ? openingBalance.hashCode() : 0);
        result = 31 * result + (openingBalanceEntryType != null ? openingBalanceEntryType.hashCode() : 0);
        result = 31 * result + (currentBalance != null ? currentBalance.hashCode() : 0);
        result = 31 * result + (currentBalanceEntryType != null ? currentBalanceEntryType.hashCode() : 0);
        result = 31 * result + (ledgerType != null ? ledgerType.hashCode() : 0);
        result = 31 * result + reconciliation;
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}