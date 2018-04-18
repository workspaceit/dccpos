package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.AuthCredential;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.service.AuthCredentialService;
import com.workspaceit.dccpos.util.ServiceResponse;
import com.workspaceit.dccpos.validation.form.authcredential.ChangePasswordForm;
import com.workspaceit.dccpos.validation.validator.AuthCredentialValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointAuth +"/auth-credential")
public class AuthCredentialEndPoint {
    private AuthCredentialService authCredentialService;
    private AuthCredentialValidator authCredentialValidator;

    @Autowired
    public void setAuthCredentialService(AuthCredentialService authCredentialService) {
        this.authCredentialService = authCredentialService;
    }

    @Autowired
    public void setAuthCredentialValidator(AuthCredentialValidator authCredentialValidator) {
        this.authCredentialValidator = authCredentialValidator;
    }
    @RequestMapping(value = "/change-password",method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(Authentication authentication, @Valid  ChangePasswordForm changePasswordForm, BindingResult bindingResult){
        AuthCredential currentUser = (AuthCredential)authentication.getPrincipal();
        int id = currentUser.getId();

        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.authCredentialValidator.validateChangePassword(changePasswordForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        try {
            this.authCredentialService.changePassword(id,changePasswordForm);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse
                    .setValidationError("id",entityNotFound.getMessage())
                    .getFormError());
        }

        return ResponseEntity.ok(ServiceResponse.getMsgInMap("Password is changed"));
    }
    @RequestMapping(value = "/change-password/{id}",method = RequestMethod.POST)
    public ResponseEntity<?> changePasswordSubmit(@PathVariable("id") int id, @Valid  ChangePasswordForm changePasswordForm, BindingResult bindingResult){
        ServiceResponse serviceResponse = ServiceResponse.getInstance();

        this.authCredentialValidator.validateChangePassword(changePasswordForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }

        try {
            this.authCredentialService.changePassword(id,changePasswordForm);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse
                    .setValidationError("id",entityNotFound.getMessage())
                    .getFormError());
        }

        return ResponseEntity.ok(ServiceResponse.getMsgInMap("Password is changed"));
    }



}
