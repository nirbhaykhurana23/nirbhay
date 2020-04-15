package com.project.project.validations;

import com.project.project.dto.CustomerRegisterDto;
import com.project.project.dto.ForgotPasswordDto;
import com.project.project.dto.SellerRegisterDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidation
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){

        if(obj instanceof SellerRegisterDto){
            SellerRegisterDto seller = (SellerRegisterDto) obj;
            return seller.getPassword().equals(seller.getConfirmPassword());
        }
        else if(obj instanceof ForgotPasswordDto){
            ForgotPasswordDto passwords = (ForgotPasswordDto) obj;
            return passwords.getPassword().equals(passwords.getConfirmPassword());
        }
        else{
            CustomerRegisterDto customer = (CustomerRegisterDto) obj;
            return customer.getPassword().equals(customer.getConfirmPassword());
        }
    }
}
