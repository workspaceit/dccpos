package com.workspaceit.dccpos.entity.accounting;

import com.workspaceit.dccpos.constant.accounting.ACCOUNTING_ENTRY;
import com.workspaceit.dccpos.constant.accounting.LEDGER_CODE;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private LEDGER_CODE code;

    @Column(name = "name")
    private String name;


    @Column(name =  "op_balance")
    private double openingBalance;

    @Enumerated(EnumType.STRING)
    @Column(name =  "op_balance_dc")
    private ACCOUNTING_ENTRY openingBalanceEntryType;

    @Column(name =  "current_balance")
    private double currentBalance;

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

    public LEDGER_CODE getCode() {
        return code;
    }

    public void setCode(LEDGER_CODE code) {
        this.code = code;
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

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
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
        if (Double.compare(ledger.openingBalance, openingBalance) != 0) return false;
        if (Double.compare(ledger.currentBalance, currentBalance) != 0) return false;
        if (reconciliation != ledger.reconciliation) return false;
        if (groupAccount != null ? !groupAccount.equals(ledger.groupAccount) : ledger.groupAccount != null)
            return false;
        if (personalInformation != null ? !personalInformation.equals(ledger.personalInformation) : ledger.personalInformation != null)
            return false;
        if (company != null ? !company.equals(ledger.company) : ledger.company != null) return false;
        if (code != ledger.code) return false;
        if (name != null ? !name.equals(ledger.name) : ledger.name != null) return false;
        if (openingBalanceEntryType != ledger.openingBalanceEntryType) return false;
        if (currentBalanceEntryType != ledger.currentBalanceEntryType) return false;
        if (ledgerType != ledger.ledgerType) return false;
        if (notes != null ? !notes.equals(ledger.notes) : ledger.notes != null) return false;
        return createdAt != null ? createdAt.equals(ledger.createdAt) : ledger.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (groupAccount != null ? groupAccount.hashCode() : 0);
        result = 31 * result + (personalInformation != null ? personalInformation.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(openingBalance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (openingBalanceEntryType != null ? openingBalanceEntryType.hashCode() : 0);
        temp = Double.doubleToLongBits(currentBalance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (currentBalanceEntryType != null ? currentBalanceEntryType.hashCode() : 0);
        result = 31 * result + (ledgerType != null ? ledgerType.hashCode() : 0);
        result = 31 * result + reconciliation;
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}