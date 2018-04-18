package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.ResetPasswordToken;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.MailHelper;
import com.workspaceit.dccpos.service.AuthCredentialService;
import com.workspaceit.dccpos.service.ResetPasswordTokenService;
import com.workspaceit.dccpos.util.ServiceResponse;
import com.workspaceit.dccpos.validation.form.authcredential.PasswordResetForm;
import com.workspaceit.dccpos.validation.validator.PasswordValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointPublic +"/reset-password")
public class PasswordManagementEndPoint {
    private ResetPasswordTokenService resetPasswordTokenService;
    private AuthCredentialService authCredentialService;
    private MailHelper emailHelper;
    private PasswordEncoder passwordEncoder;
    private PasswordValidator passwordValidator;


    @Autowired
    public void setResetPasswordTokenService(ResetPasswordTokenService resetPasswordTokenService) {
        this.resetPasswordTokenService = resetPasswordTokenService;
    }

    @Autowired
    public void setAuthCredentialService(AuthCredentialService authCredentialService) {
        this.authCredentialService = authCredentialService;
    }

    @Autowired
    public void setEmailHelper(MailHelper emailHelper) {
        this.emailHelper = emailHelper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setPasswordValidator(PasswordValidator passwordValidator) {
        this.passwordValidator = passwordValidator;
    }

    @RequestMapping(value = "/request-new",method = RequestMethod.POST)
    public ResponseEntity<?> resetPasswordSubmit(@RequestParam("email") String email){
        ServiceResponse serviceResponse = ServiceResponse.getInstance();

        ResetPasswordToken resetPasswordToken = null;
        try {
            resetPasswordToken = this.resetPasswordTokenService.create(email);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse
                    .setValidationError("email","Email not found")
                    .getFormError());
        }

        this.emailHelper.sendPasswordResetMail(resetPasswordToken);

        return ResponseEntity.ok(ServiceResponse.getMsgInMap("Password reset email is sent"));
    }

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<?> resetPasswordSubmit(@Valid PasswordResetForm passwordResetForm,
                                                 BindingResult bindingResult){


        /**
         * Validation Starts
         * */
        String token = passwordResetForm.getToken();
        ServiceResponse serviceResponse = ServiceResponse.getInstance();
        this.passwordValidator.validateResetPassword(passwordResetForm,bindingResult);

        if(bindingResult.hasErrors()){
            serviceResponse.bindValidationError(bindingResult);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse.getFormError());
        }



        ResetPasswordToken resetPasswordToken = null;
        try {
            resetPasswordToken = this.resetPasswordTokenService.getByToken(token);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse
                    .setValidationError("token","Invalid Token")
                    .getFormError());
        }

        boolean isExpired = this.resetPasswordTokenService.isResetPasswordTokenExpired(resetPasswordToken);
        if(isExpired){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(serviceResponse
                                                                                .setValidationError("token","Token is expired")
                                                                                .getFormError());
        }

        /*** Validation ends ***/


        /**
         * Update Auth credential with new password
         * And remove reset password token
         * */
        try {
            this.authCredentialService.resetPassword(resetPasswordToken,passwordResetForm);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ServiceResponse.getMsgInMap("Please try later"));
        }

        this.resetPasswordTokenService.remove(resetPasswordToken);


        return ResponseEntity.ok(ServiceResponse.getMsgInMap("Password reset successfully"));
    }

}
