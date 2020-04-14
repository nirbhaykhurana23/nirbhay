package com.project.project.entities;

import javax.persistence.*;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_user_id")
    private Customer customer;

    Integer quantity;

    Boolean is_wishlist_item;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getIs_wishlist_item() {
        return is_wishlist_item;
    }

    public void setIs_wishlist_item(Boolean is_wishlist_item) {
        this.is_wishlist_item = is_wishlist_item;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }
}
