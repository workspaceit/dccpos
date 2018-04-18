package com.workspaceit.dccpos.config.security.service;

import com.workspaceit.dccpos.config.security.details.AuthCredentialDetails;
import com.workspaceit.dccpos.entity.AuthCredential;
import com.workspaceit.dccpos.service.AuthCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("authCredentialDetailsService")
public class AuthCredentialDetailsService implements UserDetailsService {

    private AuthCredentialService authCredentialService;

    @Autowired
    public void setAuthCredentialService(AuthCredentialService authCredentialService) {
        this.authCredentialService = authCredentialService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AuthCredential authCredential = this.authCredentialService.getByEmail(s);
        if(authCredential==null){
            throw new UsernameNotFoundException("Email or password is wrong");
        }

        return new AuthCredentialDetails(authCredential);
    }
}