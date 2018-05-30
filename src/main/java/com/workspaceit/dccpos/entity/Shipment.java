package com.workspaceit.dccpos.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workspaceit.dccpos.config.PersistenceConfig;
import com.workspaceit.dccpos.constant.SHIPMENT_COST;
import com.workspaceit.dccpos.entity.accounting.Entry;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tracking_id")
    private String trackingId;

    @ManyToOne
    @JoinColumn(name = "supplier_id",referencedColumnName = "id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entry_id",referencedColumnName = "id")
    private Entry entry;

    @OneToMany
    @JoinColumn(name = "shipment_id",referencedColumnName = "id")
    private List<Inventory> inventories;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipment_id",referencedColumnName = "id")
    @MapKey(name = "name")
    private Map<SHIPMENT_COST,ShipmentCost> costs;


    @Column(name = "total_quantity")
    private int totalQuantity;

    @Column(name = "total_product_price")
    private double totalProductPrice;

    @Column(name = "total_cost")
    private double totalCost;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "shipment_id")
    private Set<ShipmentTransaction> transactions;

    @Column(name = "total_paid")
    private double totalPaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchased_by")
    private Employee purchasedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = PersistenceConfig.DateConfig.timeZone)
    @Column(name = "purchased_date")
    private Date purchasedDate;

    @JsonIgnore
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public Map<SHIPMENT_COST, ShipmentCost> getCosts() {
        return costs;
    }

    public void setCosts(Map<SHIPMENT_COST, ShipmentCost> costs) {
        this.costs = costs;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(double totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Set<ShipmentTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<ShipmentTransaction> transactions) {
        this.transactions = transactions;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Employee getPurchasedBy() {
        return purchasedBy;
    }

    public void setPurchasedBy(Employee purchasedBy) {
        this.purchasedBy = purchasedBy;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shipment shipment = (Shipment) o;

        if (id != shipment.id) return false;
        if (totalQuantity != shipment.totalQuantity) return false;
        if (Double.compare(shipment.totalProductPrice, totalProductPrice) != 0) return false;
        if (Double.compare(shipment.totalCost, totalCost) != 0) return false;
        if (Double.compare(shipment.totalPaid, totalPaid) != 0) return false;
        if (trackingId != null ? !trackingId.equals(shipment.trackingId) : shipment.trackingId != null) return false;
        if (supplier != null ? !supplier.equals(shipment.supplier) : shipment.supplier != null) return false;
        if (entry != null ? !entry.equals(shipment.entry) : shipment.entry != null) return false;
        if (inventories != null ? !inventories.equals(shipment.inventories) : shipment.inventories != null)
            return false;
        if (costs != null ? !costs.equals(shipment.costs) : shipment.costs != null)
            return false;
        if (transactions != null ? !transactions.equals(shipment.transactions) : shipment.transactions != null)
            return false;
        if (purchasedBy != null ? !purchasedBy.equals(shipment.purchasedBy) : shipment.purchasedBy != null)
            return false;
        if (purchasedDate != null ? !purchasedDate.equals(shipment.purchasedDate) : shipment.purchasedDate != null)
            return false;
        return createdAt != null ? createdAt.equals(shipment.createdAt) : shipment.createdAt == null;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(id);

        return hcb.hashCode();
    }
}
