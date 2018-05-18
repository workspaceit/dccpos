package com.workspaceit.dccpos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.workspaceit.dccpos.constant.INVENTORY_CYCLE;
import com.workspaceit.dccpos.constant.STOCK_STATUS;
import com.workspaceit.dccpos.constant.PRODUCT_CONDITION;
import com.workspaceit.dccpos.jsonView.InventoryView;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "inventory")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(InventoryView.Basic.class)
    private int id;

    /*@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_id",referencedColumnName = "id",nullable = false)
    @JsonView(InventoryView.Summary.class)
    private List<InventoryDetails> inventoryDetails;*/

    @Column(name = "selling_price")
    private double sellingPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition")
    private PRODUCT_CONDITION condition;


    //@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(InventoryView.Details.class)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(InventoryView.Summary.class)
    @JoinColumn(name = "shipment_id",referencedColumnName = "id")
    private Shipment shipment;

    @Column(name = "purchase_price")
    @JsonView(InventoryView.Basic.class)
    private Double purchasePrice;

    @Column(name = "purchase_quantity")
    @JsonView(InventoryView.Basic.class)
    private int purchaseQuantity;

    @Column(name = "sold_quantity")
    @JsonView(InventoryView.Basic.class)
    private int soldQuantity;

    @Column(name = "available_quantity")
    @JsonView(InventoryView.Basic.class)
    private int availableQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @JsonView(InventoryView.Basic.class)
    private STOCK_STATUS status;



    @Enumerated(EnumType.STRING)
    @Column(name = "cycle")
    private INVENTORY_CYCLE inventoryCycle;

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

    /*public List<InventoryDetails> getInventoryDetails() {
        return inventoryDetails;
    }

    public void setInventoryDetails(List<InventoryDetails> inventoryDetails) {
        this.inventoryDetails = inventoryDetails;
    }*/

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }



    public STOCK_STATUS getStatus() {
        return status;
    }

    public void setStatus(STOCK_STATUS status) {
        this.status = status;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public PRODUCT_CONDITION getCondition() {
        return condition;
    }

    public void setCondition(PRODUCT_CONDITION condition) {
        this.condition = condition;
    }

    public INVENTORY_CYCLE getInventoryCycle() {
        return inventoryCycle;
    }

    public void setInventoryCycle(INVENTORY_CYCLE inventoryCycle) {
        this.inventoryCycle = inventoryCycle;
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

        Inventory inventory = (Inventory) o;

        if (id != inventory.id) return false;
        if (Double.compare(inventory.sellingPrice, sellingPrice) != 0) return false;
        if (purchaseQuantity != inventory.purchaseQuantity) return false;
        if (soldQuantity != inventory.soldQuantity) return false;
        if (availableQuantity != inventory.availableQuantity) return false;
        if (condition != inventory.condition) return false;
        if (product != null ? !product.equals(inventory.product) : inventory.product != null) return false;
        if (shipment != null ? !shipment.equals(inventory.shipment) : inventory.shipment != null) return false;
        if (purchasePrice != null ? !purchasePrice.equals(inventory.purchasePrice) : inventory.purchasePrice != null)
            return false;
        if (status != inventory.status) return false;
        if (inventoryCycle != inventory.inventoryCycle) return false;
        return createdAt != null ? createdAt.equals(inventory.createdAt) : inventory.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(sellingPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (shipment != null ? shipment.hashCode() : 0);
        result = 31 * result + (purchasePrice != null ? purchasePrice.hashCode() : 0);
        result = 31 * result + purchaseQuantity;
        result = 31 * result + soldQuantity;
        result = 31 * result + availableQuantity;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (inventoryCycle != null ? inventoryCycle.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
