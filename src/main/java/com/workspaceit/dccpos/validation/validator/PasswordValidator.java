package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.validation.form.authcredential.PasswordResetForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;


@Component
public class PasswordValidator extends BaseValidator{


    public void validatePassword(String password,Errors errors){
        if(password.length()<5){
            errors.rejectValue("password","Password must be greater then 5 character long");
        }
    }

    public void matchPasswordWithConfirmPassword(String password,String confirmPassword,Errors errors){
        if(!password.equals(confirmPassword)){
            errors.rejectValue("password","Password miss matched");

        }
    }

    public void validateResetPassword(PasswordResetForm passwordResetForm, Errors errors){

        String password = passwordResetForm.getPassword();
        String confirmPassword = passwordResetForm.getConfirmPassword();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","Password required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"confirmPassword","Confirm password required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"token","Token required");

        if(errors.hasFieldErrors("password"))return;

        this.validatePassword(password,errors);

        if(errors.hasFieldErrors("password"))return;

        this.matchPasswordWithConfirmPassword(password,confirmPassword,errors);
    }
}