package com.workspaceit.pos.config.security.details;

import com.workspaceit.pos.constant.ENTITY_STATUS;
import com.workspaceit.pos.entity.AuthCredential;
import com.workspaceit.pos.entity.PersonalInformation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class AuthCredentialDetails extends AuthCredential implements UserDetails{

    public AuthCredentialDetails(AuthCredential authCredential) {
        super();
        super.setId(authCredential.getId());
        super.setEmail(authCredential.getEmail());
        super.setPassword(authCredential.getPassword());
        super.setStatus(authCredential.getStatus());
        super.setCreatedAt(authCredential.getCreatedAt());

        super.setPersonalInformation(authCredential.getPersonalInformation());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_pos"));

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.getStatus().equals(ENTITY_STATUS.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.getStatus().equals(ENTITY_STATUS.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.getStatus().equals(ENTITY_STATUS.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return super.getStatus().equals(ENTITY_STATUS.ACTIVE);
    }

    @Override
    public String getName() {
        PersonalInformation personalInformation = super.getPersonalInformation();
        return (personalInformation!=null)?super.getPersonalInformation().getFullName():"";
    }
}
