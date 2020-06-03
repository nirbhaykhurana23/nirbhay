package com.project.project.controller;

import com.project.project.Model.CustomerRegisterModel;
import com.project.project.Model.SellerRegisterModel;
import com.project.project.entities.Customer;
import com.project.project.entities.Seller;
import com.project.project.repositories.ConfirmationTokenRepository;
import com.project.project.repositories.UserRepository;
import com.project.project.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


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

    @Autowired
    private MessageSource messageSource;

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
        Customer customer = userDaoService.getLoggedInCustomer();
        String name = customer.getFirstName();
        return messageSource.getMessage("welcome.message",new Object[]{name}, LocaleContextHolder.getLocale());
    }

    @GetMapping("/seller/home")
    public String sellerHome(){
        Seller seller= userDaoService.getLoggedInSeller();
        String name= seller.getFirstName();
        return messageSource.getMessage("welcome.message",new Object[]{name}, LocaleContextHolder.getLocale());
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
    public String createCustomer(@Valid @RequestBody CustomerRegisterModel customerRegisterModel) {
        return userDaoService.saveNewCustomer(customerRegisterModel);
    }

    @PostMapping(path = "/seller-registration")
    public String createSeller(@Valid @RequestBody SellerRegisterModel sellerRegisterModel) {
        return userDaoService.saveNewSeller(sellerRegisterModel);
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

    @PostMapping(path = "/resendActivationToken" ,consumes = "application/json")
    public String resendActivationToken(@RequestBody String email) {
        System.out.println(email);
        return userDaoService.resendActivationToken(email);
    }

}
