package com.project.project.security;


import com.project.project.CorsFilter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    AppUserDetailsService userDetailsService;

    public ResourceServerConfiguration() {
        super();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter(), SessionManagementFilter.class)

                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .antMatchers("/").anonymous()
                .antMatchers("/customer-registration").anonymous()
                .antMatchers("/seller-registration").anonymous()
                .antMatchers("/admin/home").hasAnyRole("ADMIN")
                .antMatchers("/customer/home").hasAnyRole("CUSTOMER")
                .antMatchers("/seller/home").hasAnyRole("SELLER")
                .antMatchers("/doLogout").hasAnyRole("ADMIN","CUSTOMER","SELLER")
                .antMatchers("/resendActivationToken").permitAll()

                .antMatchers("/admin/list-customers").hasAnyRole("ADMIN")
                .antMatchers("/admin/list-sellers").hasAnyRole("ADMIN")
                .antMatchers("/admin/activate-user/{uid}").hasAnyRole("ADMIN")
                .antMatchers("/admin/deactivate-user/{uid}").hasAnyRole("ADMIN")
                .antMatchers("/enableSellerAccount/{seller_id}").hasAnyRole("ADMIN")

                .antMatchers("/admin/activateProduct/{pid}").hasAnyRole("ADMIN")
                .antMatchers("/admin/deactivateProduct/{pid}").hasAnyRole("ADMIN")

                .antMatchers("/seller/activateProductVariation/{productVariationId}").hasAnyRole("SELLER")
                .antMatchers("/seller/deactivateProductVariation/{productVariationId}").hasAnyRole("SELLER")

                .antMatchers("/customer/profile").hasAnyRole("CUSTOMER")
                .antMatchers("/updateCustomerProfile").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/addresses").hasAnyRole("CUSTOMER")
                .antMatchers("/addCustomerAddress").hasAnyRole("CUSTOMER")
                .antMatchers("/deleteCustomerAddress/{address_id}").hasAnyRole("CUSTOMER")
                .antMatchers("/updateCustomerAddress/{address_id}").hasAnyRole("CUSTOMER")

                .antMatchers("/seller/profile").hasAnyRole("SELLER")
                .antMatchers("/updateSellerProfile").hasAnyRole("SELLER")
                .antMatchers("/updateSellerAddress/{address_id}").hasAnyRole("SELLER")

                .antMatchers("/confirm-account").permitAll()
                .antMatchers("/find-all-categories").permitAll()
                .antMatchers("/find-category/{category_id}").permitAll()
                .antMatchers("/find-all-products/{category_name}").permitAll()
                .antMatchers("/product/{product_id}").hasAnyRole("CUSTOMER","ADMIN")

                .antMatchers("/find-all-metadata-fields").hasAnyRole("ADMIN")
                .antMatchers("/updateCategory/{category}").hasAnyRole("ADMIN")

                .antMatchers("/seller/updateProduct/{pid}").hasAnyRole("SELLER")
                .antMatchers("/seller/deleteProduct/{pid}").hasAnyRole("SELLER")
                .antMatchers("/seller/products").hasAnyRole("SELLER")

                .antMatchers("/metadata-fields/*").hasAnyRole("ADMIN")
                .antMatchers("/save-category").hasAnyRole("ADMIN")
                .antMatchers("/save-category/{parent_category}").hasAnyRole("ADMIN")
                .antMatchers("/save-product/{category_name}").hasAnyRole("SELLER")
                .antMatchers("/save-productVariation/{product_id}").hasAnyRole("SELLER")

                .antMatchers("/add-to-cart/{productVariation_id}").hasAnyRole("CUSTOMER")
                .antMatchers("/order/{cart_id}").hasAnyRole("CUSTOMER")
                .antMatchers("/forgot-password").permitAll()
                .antMatchers("/reset-password").permitAll()
                .antMatchers("/account-unlock/{username}").permitAll()
                .antMatchers("/do-unlock").permitAll()
                .antMatchers("add-review/{product_id}").hasAnyRole("CUSTOMER")

                .antMatchers("/profile/uploadImage/{userId}").permitAll()
                .antMatchers("/product/uploadImage/{sellerid}/{pid}").hasAnyRole("SELLER")
                .antMatchers("/product-variant/uploadImage/{sellerid}/{vid}").hasAnyRole("SELLER")

                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();

    }

}
