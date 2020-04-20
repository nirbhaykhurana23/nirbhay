package com.project.project.controller;

import com.project.project.Model.ForgotPasswordModel;
import com.project.project.services.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot-password")
    public String resetPassword(@RequestBody String email){
        return forgotPasswordService.resetUserPassword(email);
    }

    @PutMapping("/reset-password")
    public String setPassword(@Valid @RequestParam("token") String resetToken, @RequestBody ForgotPasswordModel forgotPasswordModel){
        return forgotPasswordService.updatePassword(resetToken, forgotPasswordModel);
    }

}
