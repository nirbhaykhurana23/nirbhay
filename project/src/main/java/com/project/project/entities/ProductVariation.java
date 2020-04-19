package com.project.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.project.utils.HashMapConverter;

import javax.persistence.*;
import java.util.Map;

@Entity
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantityAvailable;
    private Integer price;

    private Boolean is_active;

    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> productAttributes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Map<String, Object> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(Map<String, Object> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
