package com.project.project.controller;

import com.project.project.Model.AddressModel;
import com.project.project.Model.CustomerUpdateModel;
import com.project.project.entities.Customer;
import com.project.project.services.CustomerDaoService;
import com.project.project.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class CustomerController {

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private CustomerDaoService customerDaoService;

    @GetMapping("/customer/profile")
    public MappingJacksonValue getProfileDetails(){
        Customer customer = userDaoService.getLoggedInCustomer();
        String username = customer.getUsername();

        return customerDaoService.getUserProfile(username);
    }

    @PatchMapping ("/updateCustomerProfile")
    public String updateCustomerDetails(@RequestBody CustomerUpdateModel customerUpdateModel, HttpServletResponse response){
        Customer customer1 = userDaoService.getLoggedInCustomer();
        Integer id = customer1.getUser_id();

        String message = customerDaoService.updateCustomer(customerUpdateModel,id);
        if (!message.equals("Profile updated successfully")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Error";
        }
        else
             return message;
    }

    @GetMapping("customer/addresses")
    public MappingJacksonValue getAddress(){
        Customer customer1 = userDaoService.getLoggedInCustomer();
        return customerDaoService.showAddressData(customer1);
    }

    @PostMapping("/addCustomerAddress")
    public String addCustomerAddress(@RequestBody AddressModel addressModel, HttpServletResponse response)
    {
        Customer customer1 = userDaoService.getLoggedInCustomer();
        Integer id = customer1.getUser_id();

        String message = customerDaoService.addAddress(addressModel,id);
        if (!message.equals("Address added")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @DeleteMapping("/deleteCustomerAddress/{address_id}")
    public String deleteCustomerAddress(@PathVariable Integer address_id, HttpServletResponse response)
    {
        Customer customer1 = userDaoService.getLoggedInCustomer();
        Integer user_id = customer1.getUser_id();

        String message = customerDaoService.deleteAddress(address_id, user_id);
        if (!message.equals("Address deleted")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PutMapping("/updateCustomerAddress/{address_id}")
    public String updateCustomerAddress(@RequestBody AddressModel addressModel, @PathVariable Integer address_id, HttpServletResponse response)
    {
        Customer customer1 = userDaoService.getLoggedInCustomer();

        String message = customerDaoService.updateAddress(addressModel,address_id);
        if (!message.equals("Address updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }




}
