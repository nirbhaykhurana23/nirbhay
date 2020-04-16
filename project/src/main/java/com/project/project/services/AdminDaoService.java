package com.project.project.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.project.project.Exceptions.UserNotFoundException;
import com.project.project.entities.Customer;
import com.project.project.entities.Seller;
import com.project.project.entities.User;
import com.project.project.repositories.CustomerRepository;
import com.project.project.repositories.SellerRepository;
import com.project.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminDaoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public MappingJacksonValue listAllCustomer(String page, String size)
    {
        List<Customer> customers = customerRepository.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size))).getContent();

        SimpleBeanPropertyFilter filter1=SimpleBeanPropertyFilter.filterOutAllExcept("user_id","firstName","middleName","lastName","email","is_active");

        FilterProvider filterProvider=new SimpleFilterProvider().addFilter("userFilter",filter1);

        MappingJacksonValue mapping=new MappingJacksonValue(customers);
        mapping.setFilters(filterProvider);
        return mapping;
    }

    public MappingJacksonValue listAllSeller(String page,String size)
    {
        List<Seller>  sellers=sellerRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size))).getContent();
        SimpleBeanPropertyFilter filter2=SimpleBeanPropertyFilter.filterOutAllExcept("user_id","firstName","middleName","lastName","email","is_active","company_name","company_contact");

        FilterProvider filterProvider=new SimpleFilterProvider().addFilter("userFilter",filter2);

        MappingJacksonValue mapping=new MappingJacksonValue(sellers);
        mapping.setFilters(filterProvider);

        return mapping;
    }


    @Transactional
    public String activateUser(Integer uid) {

        Optional<User> user1 = userRepository.findById(uid);
        if (user1.isPresent()) {
            User user = user1.get();

            if (!user.getIs_active()) {
                user.setIs_active(true);
                userRepository.save(user);
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Account Activated!!");
                mailMessage.setText("Your account is successfully activated.");
                emailSenderService.sendEmail(mailMessage);

                return "User Activated";
            }
            else {
                return "User is already activated";
            }

        }
        else {
            throw new UserNotFoundException("Incorrect User ID");
        }
    }

    @Transactional
    public String deactivateUser(Integer uid)
    {
        Optional<User> user1 = userRepository.findById(uid);
        if (user1.isPresent()) {

            User user = user1.get();
            if(user.getIs_active())
            {
                user.setIs_active(false);
                userRepository.save(user);
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Account Deactivated!!");
                mailMessage.setText("Your account has been deactivated.");
                emailSenderService.sendEmail(mailMessage);
                return "User Deactivated";
            }
            else
            {
                return "User is already deactivated";
            }

        }
        else {
            throw new UserNotFoundException("Incorrect User ID");
        }
    }
}
