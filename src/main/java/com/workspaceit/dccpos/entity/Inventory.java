package com.workspaceit.dccpos.entity;

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

    @OneToMany
    @JoinColumn(name = "inventory_id",referencedColumnName = "id")
    private List<InventoryDetails> inventoryDetails;


    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

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

    @Column(name = "damaged_quantity")
    private int damagedQuantity;

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

    public int getDamagedQuantity() {
        return damagedQuantity;
    }

    public void setDamagedQuantity(int damagedQuantity) {
        this.damagedQuantity = damagedQuantity;
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
        if (damagedQuantity != inventory.damagedQuantity) return false;
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
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (shipment != null ? shipment.hashCode() : 0);
        result = 31 * result + (purchasePrice != null ? purchasePrice.hashCode() : 0);
        result = 31 * result + purchaseQuantity;
        result = 31 * result + soldQuantity;
        result = 31 * result + availableQuantity;
        result = 31 * result + damagedQuantity;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
