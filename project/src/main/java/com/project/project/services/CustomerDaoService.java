package com.project.project.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.project.project.Exceptions.ResourceNotFoundException;
import com.project.project.Exceptions.UserNotFoundException;
import com.project.project.dto.AddressDto;
import com.project.project.dto.CustomerUpdateDto;
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
    public String updateCustomer(CustomerUpdateDto customerUpdateDto, Integer id){
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()){
            Customer customer1= customer.get();

            if (customerUpdateDto.getUsername() != null)
                customer1.setUsername(customerUpdateDto.getUsername());

            if(customerUpdateDto.getFirstName() != null)
                customer1.setFirstName(customerUpdateDto.getFirstName());

            if(customerUpdateDto.getMiddleName() != null)
                customer1.setMiddleName(customerUpdateDto.getMiddleName());

            if(customerUpdateDto.getLastName() != null)
                customer1.setLastName(customerUpdateDto.getLastName());

            if(customerUpdateDto.getContact() != null)
                customer1.setContact(customerUpdateDto.getContact());

            if (customerUpdateDto.getEmail() != null)
                customer1.setEmail(customerUpdateDto.getEmail());

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
    public String addAddress(AddressDto addressDto, Integer id){

        Optional<User> user = userRepository.findById(id);
        User user1= user.get();

        Address address = new Address();
        address.setAddress_line(addressDto.getAddress_line());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setCountry(addressDto.getCountry());
        address.setZip_code(addressDto.getZip_code());
        address.setLabel(addressDto.getLabel());
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
    public  String updateAddress(AddressDto addressDto ,Integer addressId){
        Optional<Address> address = addressRepository.findById(addressId);

        if (address.isPresent()){
            Address savedAddress= address.get();

            if(addressDto.getAddress_line() != null)
                savedAddress.setAddress_line(addressDto.getAddress_line());

            if(addressDto.getCity() != null)
                savedAddress.setCity(addressDto.getCity());

            if(addressDto.getState() != null)
                savedAddress.setState(addressDto.getState());

            if(addressDto.getCountry() != null)
                savedAddress.setCountry(addressDto.getCountry());

            if(addressDto.getZip_code() != null)
                savedAddress.setZip_code(addressDto.getZip_code());

            if(addressDto.getLabel() != null)
                savedAddress.setLabel(addressDto.getLabel());

            return "Address updated";
        }
        else {
            throw new ResourceNotFoundException("Invalid Address Id");
        }

    }

}
