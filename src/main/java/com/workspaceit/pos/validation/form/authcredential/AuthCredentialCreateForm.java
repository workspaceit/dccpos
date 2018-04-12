package com.workspaceit.pos.validation.form.authcredential;

import com.workspaceit.pos.constant.ACCESS_ROLE;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AuthCredentialCreateForm {

    @NotBlank(message="Email required")
    @Length(max=100,message = "Value is too large")
    @Pattern(regexp ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$", message="Email is not valid")
    private String email;

    @NotBlank(message="Password required")
    private String password;

    @NotBlank(message="Confirm password required")
    private String confirmPassword;

    @NotNull(message="Access role required")
    private ACCESS_ROLE accessRole;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public ACCESS_ROLE getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(ACCESS_ROLE accessRole) {
        this.accessRole = accessRole;
    }
}
