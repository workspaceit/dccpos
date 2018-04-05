package com.workspaceit.pos.api;

import com.workspaceit.pos.entity.AuthCredential;
import com.workspaceit.pos.entity.ResetPasswordToken;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.helper.EmailHelper;
import com.workspaceit.pos.service.AuthCredentialService;
import com.workspaceit.pos.service.ResetPasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PasswordManagementEndPoint {
    private ResetPasswordTokenService resetPasswordTokenService;
    private AuthCredentialService authCredentialService;
    private EmailHelper emailHelper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setResetPasswordTokenService(ResetPasswordTokenService resetPasswordTokenService) {
        this.resetPasswordTokenService = resetPasswordTokenService;
    }

    @Autowired
    public void setAuthCredentialService(AuthCredentialService authCredentialService) {
        this.authCredentialService = authCredentialService;
    }

    @Autowired
    public void setEmailHelper(EmailHelper emailHelper) {
        this.emailHelper = emailHelper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/request-reset-password",method = RequestMethod.POST)
    public ResponseEntity<?> resetPasswordSubmit(@RequestParam("email") String email){

        ResetPasswordToken resetPasswordToken = null;
        try {
            resetPasswordToken = this.resetPasswordTokenService.create(email);
        } catch (EntityNotFound entityNotFound) {
            entityNotFound.printStackTrace();
        }


        this.emailHelper.sendPasswordResetMail(resetPasswordToken);

        return ResponseEntity.ok(resetPasswordToken);
    }

    @RequestMapping(value = "/submit-reset-password",method = RequestMethod.POST)
    public ResponseEntity<?> resetPasswordSubmit(@RequestParam("token") String token,
                                                 @RequestParam("password") String password,
                                                 @RequestParam("confirmPassword") String confirmPassword){


        /**
         * Implement validation
         * */




        ResetPasswordToken resetPasswordToken = null;
        try {
            resetPasswordToken = this.resetPasswordTokenService.getByToken(token);
        } catch (EntityNotFound entityNotFound) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Token not found");
        }
        boolean isExpired = this.resetPasswordTokenService.isResetPasswordTokenExpired(resetPasswordToken);

        if(isExpired){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Token not expired");
        }

        AuthCredential authCredential = resetPasswordToken.getAuthCredential();
        authCredential.setPassword(this.passwordEncoder.encode(password));

        this.authCredentialService.update(authCredential);

        return ResponseEntity.ok("Password reset");
    }

}
