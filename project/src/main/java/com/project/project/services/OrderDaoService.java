package com.project.project.services;

import com.project.project.Exceptions.ResourceNotFoundException;
import com.project.project.entities.*;
import com.project.project.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public String addToOrder(Integer customer_user_id, Orders orders, Integer cart_id) {

        Optional<Cart> cartId = cartRepository.findById(cart_id);

        if (cartId.isPresent()) {
            Cart cart = new Cart();
            cart = cartId.get();

            Customer customer = cart.getCustomer();

            orders.setCustomer(customer);

            Address address = new Address();
            String address_label = orders.getCustomer_address_label();
            Optional<Address> address1 = addressRepository.findByAdd(address_label, customer_user_id);
            if (address1.isPresent()) {
                address = address1.get();

                orders.setCustomer_address_address_line(address.getAddress_line());
                orders.setCustomer_address_city(address.getCity());
                orders.setCustomer_address_state(address.getState());
                orders.setCustomer_address_country(address.getCountry());
                orders.setCustomer_address_zip_code(address.getZip_code());
                orders.setDate_created(new Date());

            }
            else {
                throw new ResourceNotFoundException("Address not found, Check Address Label");
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(orders);
            orderProduct.setProductVariation(cart.getProductVariation());
            orderProduct.setQuantity(cart.getQuantity());

            ProductVariation product_variation = cart.getProductVariation();
            if (product_variation.getIs_active()) {
                orderProduct.setPrice(product_variation.getPrice());

                Integer amount = orderProduct.getPrice() * cart.getQuantity();
                orders.setAmount_paid(amount);

                Integer originalqty = product_variation.getQuantityAvailable();
                Integer orderedqty = cart.getQuantity();

                if (originalqty > orderedqty) {
                    product_variation.setQuantityAvailable(originalqty - orderedqty);

                    productVariationRepository.save(product_variation);
                    orderProductRepository.save(orderProduct);
                    orderRepository.save(orders);

                    return "Order Placed Successfully.";
                }
                else {
                    throw new ResourceNotFoundException("Sorry the Requested quantity is not yet available in warehouse.");
                }
            }
            else {
                throw new ResourceNotFoundException("Requested variant is unavailable at the moment");
            }
        }
         else {
            throw new ResourceNotFoundException("Invalid Cart ID");
         }

    }

}
