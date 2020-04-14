package com.project.project.services;

import com.project.project.entities.*;
import com.project.project.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDaoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public Orders addToOrder(Integer customer_user_id, Orders orders, Integer cart_id){

        Optional<User> customer = userRepository.findById(customer_user_id);

        User user=new User();
        user=customer.get();

        Customer customer1=new Customer();
        customer1=(Customer)user;

        orders.setCustomer(customer1);

        Address address= new Address();
        String address_label= orders.getCustomer_address_label();
        Optional<Address> address1= addressRepository.findByAdd(address_label, customer_user_id);
        address= address1.get();

        orders.setCustomer_address_address_line(address.getAddress_line());
        orders.setCustomer_address_city(address.getCity());
        orders.setCustomer_address_country(address.getCountry());
        orders.setCustomer_address_state(address.getState());
        orders.setCustomer_address_zip_code(address.getZip_code());
        orders.setDate_created(new Date());

        orderRepository.save(orders);

        Optional<Cart> cartId= cartRepository.findById(cart_id);
        Cart cart= new Cart();
        cart= cartId.get();

        OrderProduct orderProduct= new OrderProduct();
        orderProduct.setOrder(orders);
        orderProduct.setProductVariation(cart.getProductVariation());
        orderProduct.setQuantity(cart.getQuantity());

        ProductVariation product_variation= cart.getProductVariation();

        orderProduct.setPrice(product_variation.getPrice());

        orderProductRepository.save(orderProduct);

        return orders;
    }

}
