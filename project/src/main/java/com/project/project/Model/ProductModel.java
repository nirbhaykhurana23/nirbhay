package com.project.project.Model;

import javax.validation.constraints.NotNull;

public class ProductModel {

    @NotNull
    private String name;

    @NotNull
    private String brand;

    @NotNull
    private String description;

    @NotNull
    private Boolean is_returnable;

    @NotNull
    private Boolean is_cancellable;

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

    public Boolean getIs_returnable() {
        return is_returnable;
    }

    public void setIs_returnable(Boolean is_returnable) {
        this.is_returnable = is_returnable;
    }

    public Boolean getIs_cancellable() {
        return is_cancellable;
    }

    public void setIs_cancellable(Boolean is_cancellable) {
        this.is_cancellable = is_cancellable;
    }

}
