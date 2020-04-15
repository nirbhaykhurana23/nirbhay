package com.project.project.controller;

import com.project.project.dto.ForgotPasswordDto;
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
        String message = forgotPasswordService.resetUserPassword(email);
        return message;
    }

    @PutMapping("/reset-password")
    public String setPassword(@Valid @RequestParam("token") String resetToken, @RequestBody ForgotPasswordDto forgotPasswordDto){
        String message =forgotPasswordService.updatePassword(resetToken,forgotPasswordDto);
        return message;
    }

}
