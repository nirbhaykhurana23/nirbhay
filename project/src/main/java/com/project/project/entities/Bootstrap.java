package com.project.project.entities;

import com.project.project.repositories.RoleRepository;
import com.project.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Optional<User> saved_user=userRepository.findById(1);
        if(!saved_user.isPresent())
        {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Role admin=new Role();
            admin.setAuthority("ROLE_ADMIN");
            admin.setId(1);
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("Nirbhay@23"));
            user.setEmail("admin@tothenew.com");
            user.setFirstName("admin");
            user.setIs_enabled(true);
            user.setIs_nonLocked(true);
            user.setIs_deleted(false);
            user.setRoles(Arrays.asList(admin));
            userRepository.save(user);
        }

        Role customer = new Role(2,"ROLE_CUSTOMER");
        Role seller = new Role(3,"ROLE_SELLER");

        roleRepository.save(customer);
        roleRepository.save(seller);
    }
}
