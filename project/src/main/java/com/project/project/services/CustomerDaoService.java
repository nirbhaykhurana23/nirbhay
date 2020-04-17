package com.project.project.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.project.project.Exceptions.ResourceNotFoundException;
import com.project.project.Exceptions.UserNotFoundException;
import com.project.project.Model.AddressModel;
import com.project.project.Model.CustomerUpdateModel;
import com.project.project.entities.Address;
import com.project.project.entities.Customer;
import com.project.project.entities.User;
import com.project.project.repositories.AddressRepository;
import com.project.project.repositories.CustomerRepository;
import com.project.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CustomerDaoService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private UserRepository userRepository;

    public MappingJacksonValue getUserProfile(String username){
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if(customer.isPresent()) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("user_id", "firstName", "middleName", "lastName", "contact", "is_active");

            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", filter);

            MappingJacksonValue mapping = new MappingJacksonValue(customer);
            mapping.setFilters(filterProvider);
            return mapping;
        }
        else
        {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    @Modifying
    public String updateCustomer(CustomerUpdateModel customerUpdateModel, Integer id){
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()){
            Customer customer1= customer.get();

            if (customerUpdateModel.getUsername() != null)
                customer1.setUsername(customerUpdateModel.getUsername());

            if(customerUpdateModel.getFirstName() != null)
                customer1.setFirstName(customerUpdateModel.getFirstName());

            if(customerUpdateModel.getMiddleName() != null)
                customer1.setMiddleName(customerUpdateModel.getMiddleName());

            if(customerUpdateModel.getLastName() != null)
                customer1.setLastName(customerUpdateModel.getLastName());

            if(customerUpdateModel.getContact() != null)
                customer1.setContact(customerUpdateModel.getContact());

            if (customerUpdateModel.getEmail() != null)
                customer1.setEmail(customerUpdateModel.getEmail());

            customerRepository.save(customer1);
            return "Profile updated successfully";
        }
        else
            throw new UserNotFoundException("User not found");

    }

    public  MappingJacksonValue showAddressData(Customer customer)
    {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("addresses");
            FilterProvider filterProvider=new SimpleFilterProvider().addFilter("userFilter",filter);

            MappingJacksonValue mapping=new MappingJacksonValue(customer);
            mapping.setFilters(filterProvider);
            return mapping;
    }

    @Transactional
    @Modifying
    public String addAddress(AddressModel addressModel, Integer id){

        Optional<User> user = userRepository.findById(id);
        User user1= user.get();

        Address address = new Address();
        address.setAddress_line(addressModel.getAddress_line());
        address.setCity(addressModel.getCity());
        address.setState(addressModel.getState());
        address.setCountry(addressModel.getCountry());
        address.setZip_code(addressModel.getZip_code());
        address.setLabel(addressModel.getLabel());
        address.setUser(user1);
        addressRepository.save(address);
        return "Address added";
    }

    @Transactional
    @Modifying
    public String deleteAddress(Integer address_id, Integer user_id){
        Optional<Address> address = addressRepository.findById(address_id);
        Optional<Customer> id = customerRepository.findById(user_id);

        if (address.isPresent()){
            addressRepository.deleteAdd(user_id, address_id);
            return "Address deleted";
        }
        else
            throw new ResourceNotFoundException("Invalid Address Id");
    }

    @Transactional
    @Modifying
    public  String updateAddress(AddressModel addressModel, Integer addressId){
        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isPresent()){
            Address savedAddress= address.get();

            if(addressModel.getAddress_line() != null)
                savedAddress.setAddress_line(addressModel.getAddress_line());

            if(addressModel.getCity() != null)
                savedAddress.setCity(addressModel.getCity());

            if(addressModel.getState() != null)
                savedAddress.setState(addressModel.getState());

            if(addressModel.getCountry() != null)
                savedAddress.setCountry(addressModel.getCountry());

            if(addressModel.getZip_code() != null)
                savedAddress.setZip_code(addressModel.getZip_code());

            if(addressModel.getLabel() != null)
                savedAddress.setLabel(addressModel.getLabel());

            return "Address updated";
        }
        else {
            throw new ResourceNotFoundException("Invalid Address Id");
        }

    }

}
