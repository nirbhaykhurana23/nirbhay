package com.project.project.controller;

import com.project.project.services.AdminDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @Autowired
    private AdminDaoService adminDaoService;

    @GetMapping("/admin/list-customers")
    public MappingJacksonValue getCustomers(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size){
        return adminDaoService.listAllCustomer(page, size);
    }

    @GetMapping("/admin/list-sellers")
    public MappingJacksonValue getSellers(@RequestParam(defaultValue = "0")String page, @RequestParam(defaultValue = "10")String size) {
        return adminDaoService.listAllSeller(page, size);
    }

    @PatchMapping("/admin/activate-user/{uid}")
    public String userActivation(@PathVariable Integer uid) {
        return adminDaoService.activateUser(uid);
    }

    @PatchMapping("/admin/deactivate-user/{uid}")
    public String userDeactivation(@PathVariable Integer uid) {
        return adminDaoService.deactivateUser(uid);
    }

}
