package com.project.project.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.project.project.Model.ProductModel;
import com.project.project.Model.ProductUpdateModel;
import com.project.project.Model.ProductVariationModel;
import com.project.project.entities.Seller;
import com.project.project.services.ProductDaoService;
import com.project.project.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductDaoService productDaoService;

    @Autowired
    private UserDaoService userDaoService;

    @PostMapping("/save-product/{category_name}")
    public String saveProduct(@Valid @RequestBody List<ProductModel> productModels, @PathVariable String category_name){

        Seller seller = userDaoService.getLoggedInSeller();

        return productDaoService.saveNewProduct(productModels, category_name, seller);
    }

    @PostMapping("/save-productVariation/{product_id}")
    public String saveProductVariation(@Valid @RequestBody ProductVariationModel productVariationModel, @PathVariable Long product_id){

        Seller seller = userDaoService.getLoggedInSeller();

        return productDaoService.saveNewProductVariation(productVariationModel, product_id, seller);
    }

    @PatchMapping("/admin/activateProduct/{pid}")
    public String productActivation(@PathVariable Long pid) {
        return productDaoService.activateProduct(pid);
    }

    @PatchMapping("/admin/deactivateProduct/{pid}")
    public String productDeactivation(@PathVariable Long pid) {
        return productDaoService.deactivateProduct(pid);
    }


    @PatchMapping("/seller/activateProductVariation/{productVariationId}")
    public String productVariationActivation(@PathVariable Integer productVariationId) {
        return productDaoService.activateProductVariation(productVariationId);
    }

    @PatchMapping("/seller/deactivateProductVariation/{productVariationId}")
    public String productVariationDeactivation(@PathVariable Integer productVariationId) {
        return productDaoService.deactivateProductVariation(productVariationId);
    }


    @GetMapping("/find-all-products/{category_name}")
    public MappingJacksonValue retrieveProductList(@PathVariable String category_name) {
        return productDaoService.retrieveProductList(category_name);
    }

    @GetMapping("/product/{product_id}")
    public MappingJacksonValue retrieveProduct(@PathVariable Long product_id) {
        return productDaoService.retrieveProduct(product_id);
    }

    @PutMapping("/seller/updateProduct/{pid}")
    public ResponseEntity<Object> updateProductDetails(@RequestBody ProductUpdateModel productUpdateModel, @PathVariable Long pid){
        Seller seller = userDaoService.getLoggedInSeller();
        Integer sellerid = seller.getUser_id();
        String message = productDaoService.updateProduct(productUpdateModel, pid, sellerid);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping("/seller/deleteProduct/{pid}")
    public ResponseEntity<Object> deleteProductVariation(@PathVariable Integer pid) {

        Seller seller = userDaoService.getLoggedInSeller();
        Integer sellerid = seller.getUser_id();

        String message = productDaoService.deleteProductVariation(pid, sellerid);

        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @GetMapping("/seller/products")
    public MappingJacksonValue retrieveSellerProducts() {
        Seller seller = userDaoService.getLoggedInSeller();
        return productDaoService.retrieveSellerProducts(seller);
    }





}
