package com.project.project.services;

import com.project.project.entities.Category;
import com.project.project.entities.Product;
import com.project.project.entities.Seller;
import com.project.project.entities.User;
import com.project.project.repositories.CategoryRepository;
import com.project.project.repositories.ProductRepository;
import com.project.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDaoService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    Category category;

    public List<Product> saveNewProduct(Integer seller_user_id, List<Product> product, String category_name){

        Optional<User> seller=userRepository.findById(seller_user_id);

        User seller1=new User();
        seller1=seller.get();

        Seller seller2= new Seller();
        seller2=(Seller)seller1;

        //product.forEach(e->e.setSeller(seller2));

        Seller finalSeller = seller2;
        product.forEach(e->e.setSeller(finalSeller));

        Optional<Category> category1 = categoryRepository.findByName(category_name);

        category=category1.get();

        product.forEach(e->e.setCategory(category));

        productRepository.saveAll(product);
        return product;
    }

    public List<Object[]> findAllProducts(String category_name){
        String category=categoryRepository.findByCatName(category_name);
        return productRepository.findAllProducts(category);
    }


}
