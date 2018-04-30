package com.workspaceit.dccpos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workspaceit.dccpos.constant.INVENTORY_STATUS;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_id",referencedColumnName = "id",nullable = false)
    private List<InventoryDetails> inventoryDetails;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shipment_id",referencedColumnName = "id")
    private Shipment shipment;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "purchase_quantity")
    private int purchaseQuantity;

    @Column(name = "sold_quantity")
    private int soldQuantity;

    @Column(name = "available_quantity")
    private int availableQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private INVENTORY_STATUS status;



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

    public List<InventoryDetails> getInventoryDetails() {
        return inventoryDetails;
    }

    public void setInventoryDetails(List<InventoryDetails> inventoryDetails) {
        this.inventoryDetails = inventoryDetails;
    }

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



    public INVENTORY_STATUS getStatus() {
        return status;
    }

    public void setStatus(INVENTORY_STATUS status) {
        this.status = status;
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
        if (purchaseQuantity != inventory.purchaseQuantity) return false;
        if (soldQuantity != inventory.soldQuantity) return false;
        if (availableQuantity != inventory.availableQuantity) return false;
        if (inventoryDetails != null ? !inventoryDetails.equals(inventory.inventoryDetails) : inventory.inventoryDetails != null)
            return false;
        if (product != null ? !product.equals(inventory.product) : inventory.product != null) return false;
        if (shipment != null ? !shipment.equals(inventory.shipment) : inventory.shipment != null) return false;
        if (purchasePrice != null ? !purchasePrice.equals(inventory.purchasePrice) : inventory.purchasePrice != null)
            return false;
        if (status != inventory.status) return false;
        return createdAt != null ? createdAt.equals(inventory.createdAt) : inventory.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (inventoryDetails != null ? inventoryDetails.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (shipment != null ? shipment.hashCode() : 0);
        result = 31 * result + (purchasePrice != null ? purchasePrice.hashCode() : 0);
        result = 31 * result + purchaseQuantity;
        result = 31 * result + soldQuantity;
        result = 31 * result + availableQuantity;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
