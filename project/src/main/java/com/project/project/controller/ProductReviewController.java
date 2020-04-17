package com.project.project.controller;

import com.project.project.Model.ProductReviewModel;
import com.project.project.entities.Customer;
import com.project.project.services.ProductReviewService;
import com.project.project.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ProductReviewController {

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private UserDaoService userDaoService;

    @PostMapping("add-review/{product_id}")
    public String addReview(@Valid @RequestBody ProductReviewModel productReviewModel, @PathVariable Integer product_id){

        Customer customer = userDaoService.getLoggedInCustomer();
        Integer customer_user_id = customer.getUser_id();

        String msg= productReviewService.addReview(productReviewModel, customer_user_id, product_id);
        return msg;
    }
}
