package com.workspaceit.dccpos.config.security;

import com.workspaceit.dccpos.config.security.filter.HttpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by anik on 12/22/17.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    private UserDetailsService authCredentialDetailsService;

    @Autowired
    @Qualifier("authCredentialDetailsService")
    public void setAuthCredentialDetailsService(UserDetailsService authCredentialDetailsService) {
        this.authCredentialDetailsService = authCredentialDetailsService;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new HttpFilter(),BasicAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/api/**").permitAll()
                    //.antMatchers("/update-password*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                .and()
                    .userDetailsService(this.authCredentialDetailsService)
                    .formLogin()
                        //.loginPage("/login")
                       // .failureUrl("/login?error")
                      //  .loginProcessingUrl("/j_spring_security_check")
                        .usernameParameter("email").passwordParameter("password")
                .and()
                    .logout().logoutSuccessUrl("/login?logout")
                .and()
                    .exceptionHandling().accessDeniedPage("/403")
                .and()
                    .csrf().disable();


    }

    @Bean
    PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}