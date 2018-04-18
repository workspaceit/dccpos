package com.workspaceit.dccpos.entity.accounting;

import com.workspaceit.dccpos.entity.Employee;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "acc_entries")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "entry_type",referencedColumnName = "id")
    private EntryType entryType;

    @Column(name = "number")
    private int number;

    @Column(name = "date")
    private Date date;

    @Column(name = "dr_total")
    private double   drTotal;

    @Column(name = "cr_total")
    private double  crTotal;

    @Column(name = "narration")
    private String   narration;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "entry_id")
    private List<EntryItem> entryItems;

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

    public EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDrTotal() {
        return drTotal;
    }

    public void setDrTotal(double drTotal) {
        this.drTotal = drTotal;
    }

    public double getCrTotal() {
        return crTotal;
    }

    public void setCrTotal(double crTotal) {
        this.crTotal = crTotal;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public List<EntryItem> getEntryItems() {
        return entryItems;
    }

    public void setEntryItems(List<EntryItem> entryItems) {
        this.entryItems = entryItems;
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