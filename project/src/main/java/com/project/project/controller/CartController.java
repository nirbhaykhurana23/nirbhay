package com.project.project.controller;

import com.project.project.entities.Cart;
import com.project.project.entities.Customer;
import com.project.project.services.CartDaoService;
import com.project.project.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartDaoService cartDaoService;

    @Autowired
    private UserDaoService userDaoService;

    @PostMapping("add-to-cart/{productVariation_id}")
    public void addToCart(@RequestBody Cart cart,@PathVariable Integer productVariation_id){

        Customer customer = userDaoService.getLoggedInCustomer();
        Integer customer_user_id = customer.getUser_id();

        Cart cart1= cartDaoService.addToCart(customer_user_id, cart, productVariation_id);
    }

}
