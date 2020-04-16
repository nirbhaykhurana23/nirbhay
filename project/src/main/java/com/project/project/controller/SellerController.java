package com.project.project.controller;

import com.project.project.dto.AddressDto;
import com.project.project.dto.CustomerUpdateDto;
import com.project.project.dto.SellerUpdateDto;
import com.project.project.entities.Customer;
import com.project.project.entities.Seller;
import com.project.project.services.SellerDaoService;
import com.project.project.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class SellerController {

    @Autowired
    private SellerDaoService sellerDaoService;

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/seller/profile")
    public MappingJacksonValue getProfileDetails(){
        Seller seller= userDaoService.getLoggedInSeller();
        String username = seller.getUsername();

        return sellerDaoService.getUserProfile(username);
    }

    @PatchMapping("/updateSellerProfile")
    public String updateSellerDetails(@RequestBody SellerUpdateDto sellerUpdateDto, HttpServletResponse response){
        Seller seller= userDaoService.getLoggedInSeller();
        Integer id = seller.getUser_id();

        String message = sellerDaoService.updateSeller(sellerUpdateDto,id);
        if (!message.equals("Profile updated successfully")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Error";
        }
        else
            return message;
    }

    @PutMapping("/updateSellerAddress/{address_id}")
    public String updateCustomerAddress(@RequestBody AddressDto addressDto, @PathVariable Integer address_id, HttpServletResponse response)
    {
        Seller seller= userDaoService.getLoggedInSeller();

        String message = sellerDaoService.updateAddress(addressDto,address_id);
        if (!message.equals("Address updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }
}
