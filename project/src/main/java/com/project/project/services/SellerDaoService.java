package com.project.project.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.project.project.Exceptions.ResourceNotFoundException;
import com.project.project.Exceptions.UserNotFoundException;
import com.project.project.dto.AddressDto;
import com.project.project.dto.CustomerUpdateDto;
import com.project.project.dto.SellerUpdateDto;
import com.project.project.entities.Address;
import com.project.project.entities.Customer;
import com.project.project.entities.Seller;
import com.project.project.repositories.AddressRepository;
import com.project.project.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SellerDaoService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private AddressRepository addressRepository;

    public MappingJacksonValue getUserProfile(String username){
        Optional<Seller> seller = sellerRepository.findByUsername(username);
        if(seller.isPresent()) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("user_id", "firstName", "middleName", "lastName", "contact", "is_active");

            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", filter);

            MappingJacksonValue mapping = new MappingJacksonValue(seller);
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
    public String updateSeller(SellerUpdateDto sellerUpdateDto, Integer id){
        Optional<Seller> seller = sellerRepository.findById(id);

        if (seller.isPresent()){
            Seller seller1= seller.get();

            if (sellerUpdateDto.getUsername() != null)
                seller1.setUsername(sellerUpdateDto.getUsername());

            if(sellerUpdateDto.getFirstName() != null)
                seller1.setFirstName(sellerUpdateDto.getFirstName());

            if(sellerUpdateDto.getMiddleName() != null)
                seller1.setMiddleName(sellerUpdateDto.getMiddleName());

            if(sellerUpdateDto.getLastName() != null)
                seller1.setLastName(sellerUpdateDto.getLastName());

            if (sellerUpdateDto.getEmail() != null)
                seller1.setEmail(sellerUpdateDto.getEmail());

            if(sellerUpdateDto.getGst() != null)
                seller1.setGst(sellerUpdateDto.getGst());

            if (sellerUpdateDto.getCompany_name() != null)
                seller1.setCompany_name(sellerUpdateDto.getCompany_name());

            if (sellerUpdateDto.getCompany_contact() != null)
                seller1.setCompany_contact(sellerUpdateDto.getCompany_contact());

            sellerRepository.save(seller1);
            return "Profile updated successfully";
        }
        else
            throw new UserNotFoundException("User not found");

    }

    @Transactional
    @Modifying
    public  String updateAddress(AddressDto addressDto , Integer addressId){
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
