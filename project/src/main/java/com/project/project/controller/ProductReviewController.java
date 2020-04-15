package com.project.project.controller;

import com.project.project.dto.ProductReviewDto;
import com.project.project.entities.ProductReview;
import com.project.project.services.ProductReviewService;
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

    @PostMapping("add-review/{customer_user_id}/{product_id}")
    public String addReview(@Valid @RequestBody ProductReviewDto productReviewDto, @PathVariable Integer customer_user_id, @PathVariable Integer product_id){
        String msg= productReviewService.addReview(productReviewDto, customer_user_id, product_id);
        return msg;
    }
}
