package com.project.project.controller;

import com.project.project.entities.Cart;
import com.project.project.services.CartDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartDaoService cartDaoService;

    @PostMapping("add-to-cart/{customer_user_id}/{productVariation_id}")
    public void addToCart(@PathVariable Integer customer_user_id, @RequestBody Cart cart,@PathVariable Integer productVariation_id){
        Cart cart1= cartDaoService.addToCart(customer_user_id, cart, productVariation_id);
    }

}
