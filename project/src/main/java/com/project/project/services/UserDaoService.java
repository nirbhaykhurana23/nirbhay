package com.project.project.services;

import com.project.project.Exceptions.TokenExpiredException;
import com.project.project.Exceptions.UserNotFoundException;
import com.project.project.Model.CustomerRegisterModel;
import com.project.project.Model.SellerRegisterModel;
import com.project.project.entities.*;
import com.project.project.repositories.ConfirmationTokenRepository;
import com.project.project.repositories.RoleRepository;
import com.project.project.repositories.SellerRepository;
import com.project.project.repositories.UserRepository;
import com.project.project.security.AppUser;
import com.project.project.tokens.ConfirmationToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private SellerRepository sellerRepository;

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

    public String saveNewCustomer(CustomerRegisterModel customerRegisterModel) {

        User existingUser = userRepository.findByEmailIgnoreCase(customerRegisterModel.getEmail());
        if(existingUser != null)
        {
            return "This email already exists";
        }
        else {
            ModelMapper modelMapper = new ModelMapper();
            Customer customer= modelMapper.map(customerRegisterModel, Customer.class);

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
            customer.setIs_active(true);
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

    public String saveNewSeller(SellerRegisterModel sellerRegisterModel) {

        User existingUser = userRepository.findByEmailIgnoreCase(sellerRegisterModel.getEmail());
        if(existingUser != null)
        {
            return "This email already exists";
        }
        else {

            ModelMapper modelMapper = new ModelMapper();
            Seller seller= modelMapper.map(sellerRegisterModel, Seller.class);

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
            seller.setIs_active(true);
            seller.setIs_deleted(false);
            seller.setIs_nonLocked(true);

            userRepository.save(seller);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(seller.getEmail());
            mailMessage.setSubject("Complete Registration");
            mailMessage.setText("Registration successful. Admin will enable your account in some time.");
            emailSenderService.sendEmail(mailMessage);

            return "Registration successful";
        }
    }

    @Transactional
    public String enableSellerAccount(Integer sellerId){
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        if (seller.isPresent()){
            Seller seller1= new Seller();
            seller1= seller.get();

            seller1.setIs_enabled(true);
            userRepository.save(seller1);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(seller1.getEmail());
            mailMessage.setSubject("Account Enabled");
            mailMessage.setText("Your account has been enabled by admin.");
            emailSenderService.sendEmail(mailMessage);

            return "Seller enabled successfully";
        }
        else
            throw new UserNotFoundException("Seller not found");
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

    public Customer getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String username = appUser.getUsername();
        Customer customer = (Customer) userRepository.findByUsername(username);
        return customer;
    }

    public Seller getLoggedInSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String username = appUser.getUsername();
        Seller seller = (Seller) userRepository.findByUsername(username);
        return seller;
    }




}
