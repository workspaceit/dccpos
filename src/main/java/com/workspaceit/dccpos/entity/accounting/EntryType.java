package com.workspaceit.dccpos.entity.accounting;

import com.workspaceit.dccpos.constant.accounting.ENTRY_TYPES;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "acc_entry_types")
public class EntryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "label")
    private ENTRY_TYPES label;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "base_type")
    private int baseType;

    @Column(name = "numbering")
    private int numbering;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "suffix")
    private String suffix;


    @Column(name = "zero_padding")
    private int zeroPadding;

    @Column(name = "bank_cash_ledger_restriction")
    private int bankCashLedgerRestriction;

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

    public ENTRY_TYPES getLabel() {
        return label;
    }

    public void setLabel(ENTRY_TYPES label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBaseType() {
        return baseType;
    }

    public void setBaseType(int baseType) {
        this.baseType = baseType;
    }

    public int getNumbering() {
        return numbering;
    }

    public void setNumbering(int numbering) {
        this.numbering = numbering;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getZeroPadding() {
        return zeroPadding;
    }

    public void setZeroPadding(int zeroPadding) {
        this.zeroPadding = zeroPadding;
    }

    public int getBankCashLedgerRestriction() {
        return bankCashLedgerRestriction;
    }

    public void setBankCashLedgerRestriction(int bankCashLedgerRestriction) {
        this.bankCashLedgerRestriction = bankCashLedgerRestriction;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}