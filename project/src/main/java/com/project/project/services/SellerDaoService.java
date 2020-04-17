package com.project.project.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.project.project.Exceptions.ResourceNotFoundException;
import com.project.project.Exceptions.UserNotFoundException;
import com.project.project.Model.AddressModel;
import com.project.project.Model.SellerUpdateModel;
import com.project.project.entities.Address;
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
    public String updateSeller(SellerUpdateModel sellerUpdateModel, Integer id){
        Optional<Seller> seller = sellerRepository.findById(id);

        if (seller.isPresent()){
            Seller seller1= seller.get();

            if (sellerUpdateModel.getUsername() != null)
                seller1.setUsername(sellerUpdateModel.getUsername());

            if(sellerUpdateModel.getFirstName() != null)
                seller1.setFirstName(sellerUpdateModel.getFirstName());

            if(sellerUpdateModel.getMiddleName() != null)
                seller1.setMiddleName(sellerUpdateModel.getMiddleName());

            if(sellerUpdateModel.getLastName() != null)
                seller1.setLastName(sellerUpdateModel.getLastName());

            if (sellerUpdateModel.getEmail() != null)
                seller1.setEmail(sellerUpdateModel.getEmail());

            if(sellerUpdateModel.getGst() != null)
                seller1.setGst(sellerUpdateModel.getGst());

            if (sellerUpdateModel.getCompany_name() != null)
                seller1.setCompany_name(sellerUpdateModel.getCompany_name());

            if (sellerUpdateModel.getCompany_contact() != null)
                seller1.setCompany_contact(sellerUpdateModel.getCompany_contact());

            sellerRepository.save(seller1);
            return "Profile updated successfully";
        }
        else
            throw new UserNotFoundException("User not found");

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
