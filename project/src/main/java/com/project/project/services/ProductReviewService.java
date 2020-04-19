package com.project.project.services;

import com.project.project.Exceptions.ResourceNotFoundException;
import com.project.project.Exceptions.UserNotFoundException;
import com.project.project.Model.ProductReviewModel;
import com.project.project.entities.*;
import com.project.project.repositories.ProductRepository;
import com.project.project.repositories.ProductReviewRepository;
import com.project.project.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductReviewService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductRepository productRepository;

    public String addReview(ProductReviewModel productReviewModel, Integer customer_user_id, Long product_id){

        Optional<User> customer = userRepository.findById(customer_user_id);
        Optional<Product> product= productRepository.findById(product_id);

        if(!customer.isPresent())
            throw new UserNotFoundException("User not found");

        else if(!product.isPresent())
            throw new ResourceNotFoundException("Product not found");

        else {
            User user=new User();
            user=customer.get();

            Customer customer1=new Customer();
            customer1=(Customer)user;

            ModelMapper modelMapper = new ModelMapper();
            ProductReview productReview= modelMapper.map(productReviewModel, ProductReview.class);

            productReview.setCustomer(customer1);

            Product product1= new Product();
            product1= product.get();

            productReview.setProduct(product1);

            productReviewRepository.save(productReview);
            return "Review posted";
        }
    }
}
