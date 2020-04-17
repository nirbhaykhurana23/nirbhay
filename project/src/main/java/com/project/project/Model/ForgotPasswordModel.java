package com.project.project.Model;

import com.project.project.validations.PasswordMatches;
import com.project.project.validations.Password;

import javax.validation.constraints.NotNull;

@PasswordMatches
public class ForgotPasswordModel {

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
