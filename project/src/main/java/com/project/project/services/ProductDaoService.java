package com.project.project.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.project.project.Exceptions.BadRequestException;
import com.project.project.Exceptions.ResourceNotFoundException;
import com.project.project.Model.ProductModel;
import com.project.project.Model.ProductUpdateModel;
import com.project.project.Model.ProductVariationModel;
import com.project.project.entities.*;
import com.project.project.entities.Product;
import com.project.project.repositories.*;
import com.project.project.utils.StringToSetParser;
import com.google.common.collect.Sets;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.*;

@Service
public class ProductDaoService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;

    @Autowired
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    @Autowired
    private EmailSenderService emailSenderService;


    public String saveNewProduct(List<ProductModel> productModels, String category_name, Seller seller){

        Optional<Category> category1 = categoryRepository.findByName(category_name);

        if (category1.isPresent()) {
            ModelMapper modelMapper = new ModelMapper();
            Type listType=new TypeToken<List<Product>>(){}.getType();
            List<Product> products= modelMapper.map(productModels, listType);

            Category category=category1.get();

            products.forEach(e->e.setSeller(seller));
            products.forEach(e->e.setCategory(category));
            products.forEach(e->e.setIs_active(false));
            productRepository.saveAll(products);

            return "Product saved";
        }
        else
            throw new ResourceNotFoundException("Category does not exists");

    }

    public String saveNewProductVariation(ProductVariationModel productVariationModel, Long productId, Seller seller){

        Optional<Product> product= productRepository.findById(productId);

        if (product.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            ProductVariation productVariations= modelMapper.map(productVariationModel, ProductVariation.class);

            Product product1= product.get();
            Product parentProduct = product.get();

            String msg;
            msg= validateNewProductVariation(productVariationModel, parentProduct);

            if (msg.equals("success")){
                if(!parentProduct.getIs_active()){
                    return "Parent product is inactive.";
                }
                else if(!product1.getIs_active()){
                    return "Product is inactive.";
                }
                else if(productVariations.getQuantityAvailable()<=0){
                    return "Quantity should be greater than 0.";
                }
                else if(productVariations.getPrice()<=0){
                    return "Price should be greater than 0";
                }
                else {
                    productVariations.setProduct(product1);
                    productVariations.setIs_active(false);
                    productVariationRepository.save(productVariations);
                    return "Product Variation saved";
                }
            }
            return msg;
        }
        else
            throw new ResourceNotFoundException("Product does not exists");
    }

    public String validateNewProductVariation(ProductVariationModel productVariationModel, Product product){

        // check if all the fields are actually related to the product category.
        Category category = product.getCategory();
        Map<String, String> attributes = productVariationModel.getAttributes();

        List<String> receivedFields = new ArrayList<>(attributes.keySet());
        List<String> actualFields = new ArrayList<>();
        categoryMetadataFieldValuesRepository.findAllFieldsOfCategoryById(category.getId())
                .forEach((e)->{
                    actualFields.add(e[0].toString());
                });

        if(receivedFields.size() < actualFields.size()){
            return "Please provide all the fields related to the product category.";
        }

        receivedFields.removeAll(actualFields);
        if(!receivedFields.isEmpty()){
            return "Invalid fields found in the data.";
        }

        // check validity of values of fields.
        List<String> receivedFieldsCopy = new ArrayList<>(attributes.keySet());

        for (String receivedField : receivedFieldsCopy) {

            CategoryMetadataField field = categoryMetadataFieldRepository.findByName(receivedField);
            List<Object> savedValues = categoryMetadataFieldValuesRepository.findAllValuesOfCategoryField(category.getId(),field.getId());

            String values = savedValues.get(0).toString();
            Set<String> actualValueSet = StringToSetParser.toSetOfValues(values);

            String receivedValues = attributes.get(receivedField);
            Set<String> receivedValueSet = StringToSetParser.toSetOfValues(receivedValues);

            if(!Sets.difference(receivedValueSet, actualValueSet).isEmpty()){
                return "Invalid value found for field "+receivedField;
            }
        }

        return "success";
    }

    @Transactional
    public String activateProduct(Long pid) {

        Optional<Product> product = productRepository.findById(pid);
        if (product.isPresent()) {
            Product product1 = product.get();
            Seller seller = product1.getSeller();

            String emailid = seller.getEmail();

            if(!product1.getIs_active())
            {
                product1.setIs_active(true);
                productRepository.save(product1);
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                mailMessage.setTo(emailid);
                mailMessage.setSubject("Product Activated!!");
                mailMessage.setText("Your product has been Activated");
                emailSenderService.sendEmail((mailMessage));
                return "Product Activated";
            }
            else
            {
                return "Product is already Activated";
            }

        } else {
            throw new ResourceNotFoundException("Incorrect Product ID");
        }
    }

    @Transactional
    public String deactivateProduct(Long pid)
    {
        Optional<Product> product = productRepository.findById(pid);
        if (product.isPresent()) {
            Product product1 = product.get();
            Seller seller = product1.getSeller();

            String emailid = seller.getEmail();

            if(product1.getIs_active())
            {
                product1.setIs_active(false);
                productRepository.save(product1);
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                mailMessage.setTo(emailid);
                mailMessage.setSubject("Product Deactivated!!");
                mailMessage.setText("Your product has been deactivated");
                emailSenderService.sendEmail((mailMessage));
                return "Product Deactivated";
            }
            else
            {
                return "Product is already deactivated";
            }

        } else {
            throw new ResourceNotFoundException("Incorrect Product ID");
        }
    }

    @Transactional
    public String activateProductVariation(Integer productVariationId) {

        Optional<ProductVariation> productVariation = productVariationRepository.findById(productVariationId);
        if (productVariation.isPresent()) {
            ProductVariation productVariation1 = productVariation.get();

            Product product1= productVariation1.getProduct();
            Seller seller = product1.getSeller();

            String emailid = seller.getEmail();

            if(!productVariation1.getIs_active())
            {
                productVariation1.setIs_active(true);
                productVariationRepository.save(productVariation1);
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                mailMessage.setTo(emailid);
                mailMessage.setSubject("Product Variation Activated!!");
                mailMessage.setText("Your product variation has been Activated");
                emailSenderService.sendEmail((mailMessage));
                return "Product variation Activated";
            }
            else
            {
                return "Product variation is already Activated";
            }

        } else {
            throw new ResourceNotFoundException("Incorrect Product variation ID");
        }
    }

    @Transactional
    public String deactivateProductVariation(Integer productVariationId) {

        Optional<ProductVariation> productVariation = productVariationRepository.findById(productVariationId);
        if (productVariation.isPresent()) {
            ProductVariation productVariation1 = productVariation.get();

            Product product1= productVariation1.getProduct();
            Seller seller = product1.getSeller();

            String emailid = seller.getEmail();

            if(productVariation1.getIs_active())
            {
                productVariation1.setIs_active(false);
                productVariationRepository.save(productVariation1);
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                mailMessage.setTo(emailid);
                mailMessage.setSubject("Product Variation Deactivated!!");
                mailMessage.setText("Your product variation has been deactivated");
                emailSenderService.sendEmail((mailMessage));
                return "Product variation deactivated";
            }
            else
            {
                return "Product variation is already deactivated";
            }

        } else {
            throw new ResourceNotFoundException("Incorrect Product variation ID");
        }
    }


    public MappingJacksonValue retrieveProductList(String category_name){
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name","brand",
                "description","is_active","is_cancellable","is_returnable","productVariations");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("productFilter",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(findCategoryWiseProducts(category_name));

        mapping.setFilters(filterProvider);

        return mapping;
    }

    public List<Product> findCategoryWiseProducts(String category_name){
        String category=categoryRepository.findByCatName(category_name);
        return productRepository.findAllProducts(category);
    }

    public MappingJacksonValue retrieveProduct(Long product_id){

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name","brand",
                "description","is_active","is_cancellable","is_returnable","productVariations");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("productFilter",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(viewProduct(product_id));

        mapping.setFilters(filterProvider);

        return mapping;
    }

    public Product viewProduct(Long product_id) {
        Optional<Product> product = productRepository.findById(product_id);
        if(product.isPresent()) {
            return product.get();
        }
        else
            throw new ResourceNotFoundException("Invalid Product ID");
    }

    @Transactional
    @Modifying
    public String updateProduct(ProductUpdateModel productUpdateModel, Long pid, Integer sellerid) {
        Optional<Product> product = productRepository.findById(pid);

        if (product.isPresent()) {
            Product savedProduct = product.get();

            if (productUpdateModel.getName() != null)
                savedProduct.setName(productUpdateModel.getName());

            if (productUpdateModel.getBrand() != null)
                savedProduct.setBrand(productUpdateModel.getBrand());

            if (productUpdateModel.getDescription() != null)
                savedProduct.setDescription(productUpdateModel.getDescription());

            if (productUpdateModel.getIs_cancellable() != null)
                savedProduct.setIs_cancellable(productUpdateModel.getIs_cancellable());

            if (productUpdateModel.getIs_returnable() != null)
                savedProduct.setIs_returnable(productUpdateModel.getIs_returnable());

            return "Product Updated Successfully";

        } else {
            throw new ResourceNotFoundException("Product does not exist");
        }
    }

    public String deleteProductVariation(Integer pid, Integer sellerid) {
        Optional<ProductVariation> productVariation = productVariationRepository.findById(pid);

        if (productVariation.isPresent()) {
            //ProductVariation savedProduct = productVariation.get();
            productVariationRepository.deleteById(pid);
            return "Product Deleted Successfully";
        }
        else {
            throw new ResourceNotFoundException("Invalid Product ID");
        }
    }

    public MappingJacksonValue retrieveSellerProducts(Seller seller){

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","brand",
                "description","is_active","is_cancellable","is_returnable","productVariations");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("productFilter",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(findSellerwiseProducts(seller));

        mapping.setFilters(filterProvider);

        return mapping;
    }

    public List<Product> findSellerwiseProducts(Seller seller){
        Integer sellerid= seller.getUser_id();
        return productRepository.findSellerAssociatedProducts(sellerid);
    }

}
