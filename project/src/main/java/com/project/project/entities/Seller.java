package com.project.project.entities;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "seller")
@PrimaryKeyJoinColumn(name = "user_id")
public class Seller extends User {

    private Integer gst;

    private String company_name;
    private Integer company_contact;

/*    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_user_id")
    private Set<Product> products;*/

    public Seller(){}

    public Integer getGst() {
        return gst;
    }

    public void setGst(Integer gst) {
        this.gst = gst;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Integer getCompany_contact() {
        return company_contact;
    }

    public void setCompany_contact(Integer company_contact) {
        this.company_contact = company_contact;
    }
/*
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }*/
}
