package com.workspaceit.dccpos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.workspaceit.dccpos.constant.WEIGHT_UNIT;
import com.workspaceit.dccpos.jsonView.ProductView;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ProductView.Basic.class)
    private int id;

    @JsonView(ProductView.Basic.class)
    @Column(name = "name")
    private String name;


    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    @JsonView(ProductView.Summary.class)
    private Category category;


    @JsonView(ProductView.Summary.class)
    @Column(name = "weight")
    private int weight;

    @JsonView(ProductView.Summary.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "weight_unit")
    private WEIGHT_UNIT weightUnit;


    @JsonView(ProductView.Summary.class)
    @Column(name = "image")
    private String image;

    @JsonView(ProductView.Summary.class)
    @Column(name = "barcode")
    private String barcode;


    @JsonView(ProductView.Summary.class)
    @Column(name = "total_available_quantity")
    private int totalAvailableQuantity;

    @JsonView(ProductView.Summary.class)
    @Column(name = "good_quantity")
    private int goodQuantity;

    @JsonView(ProductView.Summary.class)
    @Column(name = "damaged_quantity")
    private int damagedQuantity;

    @JsonView(ProductView.Summary.class)
    @Column(name = "min_price")
    private double  minPrice;

    @JsonView(ProductView.Summary.class)
    @Column(name = "max_price")
    private double maxPrice;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private List<Inventory> inventories;

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


    public int getGoodQuantity() {
        return goodQuantity;
    }

    public void setGoodQuantity(int goodQuantity) {
        this.goodQuantity = goodQuantity;
    }

    public int getDamagedQuantity() {
        return damagedQuantity;
    }

    public void setDamagedQuantity(int damagedQuantity) {
        this.damagedQuantity = damagedQuantity;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
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
        if (goodQuantity != product.goodQuantity) return false;
        if (damagedQuantity != product.damagedQuantity) return false;
        if (Double.compare(product.minPrice, minPrice) != 0) return false;
        if (Double.compare(product.maxPrice, maxPrice) != 0) return false;
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
        int result;
        long temp;
        result = id;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + weight;
        result = 31 * result + (weightUnit != null ? weightUnit.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + totalAvailableQuantity;
        result = 31 * result + goodQuantity;
        result = 31 * result + damagedQuantity;
        temp = Double.doubleToLongBits(minPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maxPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (inventories != null ? inventories.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
