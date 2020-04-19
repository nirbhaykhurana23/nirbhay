package com.project.project.Model;

public class ProductUpdateModel {

    private String name;
    private String brand;
    private String description;
    private Boolean is_cancellable;
    private Boolean is_returnable;

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
}
