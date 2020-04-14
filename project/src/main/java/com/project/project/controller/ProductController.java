package com.project.project.controller;

import com.project.project.entities.Product;
import com.project.project.services.ProductDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductDaoService productDaoService;

    @PostMapping("/{seller_user_id}/save-product/category/{category_name}")
    public void saveProduct(@PathVariable Integer seller_user_id,@RequestBody List<Product> product, @PathVariable String category_name){
        List<Product> product1= productDaoService.saveNewProduct(seller_user_id, product, category_name);
    }

    @GetMapping("/find-all-products/{category_name}")
    public List<Object[]> findAllProducts(@PathVariable String category_name){
        return productDaoService.findAllProducts(category_name);
    }

}
