package com.project.project.services;

import com.project.project.Exceptions.TokenExpiredException;
import com.project.project.dto.CustomerRegisterDto;
import com.project.project.dto.SellerRegisterDto;
import com.project.project.entities.*;
import com.project.project.repositories.ConfirmationTokenRepository;
import com.project.project.repositories.RoleRepository;
import com.project.project.repositories.UserRepository;
import com.project.project.security.AppUser;
import com.project.project.tokens.ConfirmationToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserDaoService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    private static List<Customer> customerList=new ArrayList<>();
    private static List<Seller> sellerList=new ArrayList<>();

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if (username != null) {

/*            if(user.getAttempts()>=3){
                user.setIs_nonLocked(false);
                return new AppUser(user);
            }*/
            return new AppUser(user);
        }
        else {
            throw new RuntimeException();
        }

    }

    public List<User> findAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    public String saveNewCustomer(CustomerRegisterDto customerRegisterDto) {

        User existingUser = userRepository.findByEmailIgnoreCase(customerRegisterDto.getEmail());
        if(existingUser != null)
        {
            return "This email already exists";
        }
        else {
            ModelMapper modelMapper = new ModelMapper();
            Customer customer= modelMapper.map(customerRegisterDto, Customer.class);

            String password=customer.getPassword();
            customer.setPassword(passwordEncoder.encode(password));

            Role role=new Role();
            List<User> userList=new ArrayList<>();

            userList.add(customer);
            customerList.add(customer);

            Optional<Role> role1= roleRepository.findById(2);

            role=role1.get();

            customer.setRoles(Arrays.asList(role));

            customer.setIs_enabled(false);
            customer.setIs_deleted(false);
            customer.setIs_nonLocked(true);

            userRepository.save(customer);

            ConfirmationToken confirmationToken = new ConfirmationToken(customer);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(customer.getEmail());
            mailMessage.setSubject("Complete Registration");
            mailMessage.setText("To activate your account, please click here : "
                    +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            return "Registeration successful";
        }
    }

    public List<Customer> findAllCustomers(){
        return userRepository.findCustomers();
    }

    public String saveNewSeller(SellerRegisterDto sellerRegisterDto) {

        User existingUser = userRepository.findByEmailIgnoreCase(sellerRegisterDto.getEmail());
        if(existingUser != null)
        {
            return "This email already exists";
        }
        else {

            ModelMapper modelMapper = new ModelMapper();
            Seller seller= modelMapper.map(sellerRegisterDto, Seller.class);

            String password=seller.getPassword();
            seller.setPassword(passwordEncoder.encode(password));

            Role role=new Role();
            List<User> userList=new ArrayList<>();

            userList.add(seller);
            sellerList.add(seller);

            Optional<Role> role1= roleRepository.findById(3);

            role=role1.get();

            seller.setRoles(Arrays.asList(role));

            seller.setIs_enabled(false);
            seller.setIs_deleted(false);
            seller.setIs_nonLocked(true);

            userRepository.save(seller);

            ConfirmationToken confirmationToken = new ConfirmationToken(seller);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(seller.getEmail());
            mailMessage.setSubject("Complete Registration");
            mailMessage.setText("To activate your account, please click here : "
                    +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            return "Registeration successful";
        }
    }

    public List<Seller> findAllSellers(){
        return userRepository.findSellers();
    }

    @Transactional
    public String confirmUserAccount(String confirmationToken){
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        Date presentDate = new Date();
        if (token.getExpiryDate().getTime() - presentDate.getTime() <= 0){
            throw new TokenExpiredException("Token has been expired");
        }

        else
        {
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setIs_enabled(true);
            userRepository.save(user);
            confirmationTokenRepository.delConfirmationToken(confirmationToken);
            return "Your account is activated" ;
        }

    }




}
