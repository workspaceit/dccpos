package com.workspaceit.dccpos.validation.validator;

import com.workspaceit.dccpos.entity.AuthCredential;
import com.workspaceit.dccpos.helper.ValidationHelper;
import com.workspaceit.dccpos.service.AuthCredentialService;
import com.workspaceit.dccpos.validation.form.authcredential.AuthCredentialCreateForm;
import com.workspaceit.dccpos.validation.form.authcredential.ChangePasswordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class AuthCredentialValidator {
    private AuthCredentialService authCredentialService;

    @Autowired
    public void setAuthCredentialService(AuthCredentialService authCredentialService) {
        this.authCredentialService = authCredentialService;
    }
    public void validateChangePassword(ChangePasswordForm changePasswordForm, Errors errors){
        if(!errors.hasFieldErrors("password")){
            this.validatePassword("",changePasswordForm.getPassword(),errors);
        }
        if(!errors.hasFieldErrors("password") && !errors.hasFieldErrors("confirmPassword")){
            this.matchPassword("",changePasswordForm.getPassword(),changePasswordForm.getConfirmPassword(),errors);
        }

    }
    public void validate(String prefix, AuthCredentialCreateForm authCredentialForm, Errors errors){
        prefix = ValidationHelper.preparePrefix(prefix);

        if(!errors.hasFieldErrors(prefix+"email")){
            this.validateUniqueEmail(prefix,authCredentialForm.getEmail(),errors);
        }
        if(!errors.hasFieldErrors(prefix+"password")){
            this.validatePassword(prefix,authCredentialForm.getPassword(),errors);
        }
        if(!errors.hasFieldErrors(prefix+"password") && !errors.hasFieldErrors(prefix+"confirmPassword")){
            this.matchPassword(prefix,authCredentialForm.getPassword(),authCredentialForm.getConfirmPassword(),errors);
        }

    }
    public void validateUpdate(String prefix, AuthCredentialCreateForm authCredentialForm, Errors errors){
        prefix = ValidationHelper.preparePrefix(prefix);


        if(!errors.hasFieldErrors(prefix+"password")){
            this.validatePassword(prefix,authCredentialForm.getPassword(),errors);
        }
        if(!errors.hasFieldErrors(prefix+"password") && !errors.hasFieldErrors(prefix+"confirmPassword")){
            this.matchPassword(prefix,authCredentialForm.getPassword(),authCredentialForm.getConfirmPassword(),errors);
        }
    }
    private void validatePassword(String prefix, String password, Errors errors){
        if(password.length()<5){
            errors.rejectValue(prefix+"password","Length at least 5 character required");
        }
    }
    private void matchPassword(String prefix, String password, String confirmPassword, Errors errors){
        if(!password.equals(confirmPassword)){
            errors.rejectValue(prefix+"password","Password miss matched with confirm password");
        }
    }
    private void validateUniqueEmail(String prefix, String email, Errors errors){
        AuthCredential authCredential = this.authCredentialService.getByEmail(email);
        if(authCredential!=null){
            errors.rejectValue(prefix+"email","Email already taken");
        }
    }
}
