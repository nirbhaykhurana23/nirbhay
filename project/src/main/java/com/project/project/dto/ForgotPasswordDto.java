package com.project.project.dto;

import com.project.project.validations.PasswordMatches;
import com.project.project.validations.Password;

import javax.validation.constraints.NotNull;

@PasswordMatches
public class ForgotPasswordDto {

    @NotNull
    @Password
    private String password;

    @NotNull
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
