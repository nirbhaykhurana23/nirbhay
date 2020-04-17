package com.project.project.validations;

import com.project.project.Model.CustomerRegisterModel;
import com.project.project.Model.ForgotPasswordModel;
import com.project.project.Model.SellerRegisterModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidation
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){

        if(obj instanceof SellerRegisterModel){
            SellerRegisterModel seller = (SellerRegisterModel) obj;
            return seller.getPassword().equals(seller.getConfirmPassword());
        }
        else if(obj instanceof ForgotPasswordModel){
            ForgotPasswordModel passwords = (ForgotPasswordModel) obj;
            return passwords.getPassword().equals(passwords.getConfirmPassword());
        }
        else{
            CustomerRegisterModel customer = (CustomerRegisterModel) obj;
            return customer.getPassword().equals(customer.getConfirmPassword());
        }
    }
}
