package com.project.project.controller;

import com.project.project.entities.Customer;
import com.project.project.entities.Product;
import com.project.project.entities.Seller;
import com.project.project.services.ProductDaoService;
import com.project.project.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductDaoService productDaoService;

    @Autowired
    private UserDaoService userDaoService;


    @PostMapping("/save-product/category/{category_name}")
    public void saveProduct(@RequestBody List<Product> product, @PathVariable String category_name){

        Seller seller = userDaoService.getLoggedInSeller();
        Integer seller_user_id = seller.getUser_id();

        List<Product> product1= productDaoService.saveNewProduct(seller_user_id, product, category_name);
    }

    @GetMapping("/find-all-products/{category_name}")
    public List<Object[]> findAllProducts(@PathVariable String category_name){
        return productDaoService.findAllProducts(category_name);
    }

}
