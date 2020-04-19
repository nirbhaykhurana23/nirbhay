package com.project.project.Model;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductVariationModel {

    @NotNull
    private Integer quantityAvailable;

    @NotNull
    private Integer price;

    @NotNull
    private Boolean is_active;

    @NotNull
    private Map<String, String> attributes = new LinkedHashMap<>();

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

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

}
