package com.project.project.controller;

import com.project.project.entities.Customer;
import com.project.project.entities.Orders;
import com.project.project.services.OrderDaoService;
import com.project.project.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderController {

    @Autowired
    private OrderDaoService orderDaoService;

    @Autowired
    private UserDaoService userDaoService;

    @PostMapping("/order/{cart_id}")
    public String addToOrder(@RequestBody Orders orders, @PathVariable Integer cart_id){

        Customer customer = userDaoService.getLoggedInCustomer();
        Integer customer_user_id = customer.getUser_id();

        return orderDaoService.addToOrder(customer_user_id, orders, cart_id);
    }

}
