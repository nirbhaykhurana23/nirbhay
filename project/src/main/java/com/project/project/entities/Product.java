package com.project.project.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String brand;
    private String description;
    private Boolean is_cancellable;
    private Boolean is_returnable;
    private Boolean is_active;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Set<ProductVariation> productVariations;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_user_id")
    private Seller seller;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIs_cancellable() {
        return is_cancellable;
    }

    public void setIs_cancellable(Boolean is_cancellable) {
        this.is_cancellable = is_cancellable;
    }

    public Boolean getIs_returnable() {
        return is_returnable;
    }

    public void setIs_returnable(Boolean is_returnable) {
        this.is_returnable = is_returnable;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<ProductVariation> getProductVariations() {
        return productVariations;
    }

    public void setProductVariations(Set<ProductVariation> productVariations) {
        this.productVariations = productVariations;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
