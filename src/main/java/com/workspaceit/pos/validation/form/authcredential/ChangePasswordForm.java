package com.workspaceit.pos.validation.form.authcredential;

import org.hibernate.validator.constraints.NotBlank;

public class ChangePasswordForm {
    @NotBlank(message="Password required")
    private String password;
    @NotBlank(message="Confirm password required")
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