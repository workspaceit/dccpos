package com.workspaceit.dccpos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.workspaceit.dccpos.constant.WEIGHT_UNIT;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private int weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "weight_unit")
    private WEIGHT_UNIT weightUnit;

    @Column(name = "image")
    private String image;

    @Column(name = "barcode")
    private String barcode;


    @Column(name = "total_available_quantity")
    private int totalAvailableQuantity;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    List<Inventory> inventories;

    @JsonIgnore
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public WEIGHT_UNIT getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(WEIGHT_UNIT weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getTotalAvailableQuantity() {
        return totalAvailableQuantity;
    }

    public void setTotalAvailableQuantity(int totalAvailableQuantity) {
        this.totalAvailableQuantity = totalAvailableQuantity;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
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

        Product product = (Product) o;

        if (id != product.id) return false;
        if (weight != product.weight) return false;
        if (totalAvailableQuantity != product.totalAvailableQuantity) return false;
        if (category != null ? !category.equals(product.category) : product.category != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (weightUnit != product.weightUnit) return false;
        if (image != null ? !image.equals(product.image) : product.image != null) return false;
        if (barcode != null ? !barcode.equals(product.barcode) : product.barcode != null) return false;
        if (inventories != null ? !inventories.equals(product.inventories) : product.inventories != null) return false;
        return createdAt != null ? createdAt.equals(product.createdAt) : product.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + weight;
        result = 31 * result + (weightUnit != null ? weightUnit.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + totalAvailableQuantity;
        result = 31 * result + (inventories != null ? inventories.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
