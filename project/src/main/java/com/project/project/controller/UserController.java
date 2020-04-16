package com.project.project.controller;

import com.project.project.Exceptions.UserNotFoundException;
import com.project.project.dto.CustomerRegisterDto;
import com.project.project.dto.SellerRegisterDto;
import com.project.project.entities.*;
import com.project.project.repositories.ConfirmationTokenRepository;
import com.project.project.repositories.UserRepository;
import com.project.project.services.AccountUnlockService;
import com.project.project.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/admin/home")
    public String adminHome(){
        return "Admin home";
    }

    @GetMapping("/customer/home")
    public String userCustomer(){
        return "Customer home";
    }

    @GetMapping("/seller/home")
    public String sellerHome(){
        return "Seller home";
    }

    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }

    @PostMapping(path = "/customer-registration")
    public String createCustomer(@Valid @RequestBody CustomerRegisterDto customerRegisterDto) {
        return userDaoService.saveNewCustomer(customerRegisterDto);
    }

    @PostMapping(path = "/seller-registration")
    public String createSeller(@Valid @RequestBody SellerRegisterDto sellerRegisterDto) {
        return userDaoService.saveNewSeller(sellerRegisterDto);
    }

    @PostMapping(path = "/enableSellerAccount/{sellerId}")
    public String enableSellerAccount(@PathVariable Integer sellerId){
        return userDaoService.enableSellerAccount(sellerId);
    }

    @GetMapping("/confirm-account")
    public String confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        return userDaoService.confirmUserAccount(confirmationToken);
    }



}
