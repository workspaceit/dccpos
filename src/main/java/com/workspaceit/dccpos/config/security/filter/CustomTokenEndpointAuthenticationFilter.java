package com.workspaceit.dccpos.config.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomTokenEndpointAuthenticationFilter extends TokenEndpointAuthenticationFilter {

    public CustomTokenEndpointAuthenticationFilter(AuthenticationManager authenticationManager, OAuth2RequestFactory oAuth2RequestFactory) {

        super(authenticationManager, oAuth2RequestFactory);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
                /* before authentication check for condition if true then process to authenticate */
                System.out.println("CustomTokenEndpointAuthenticationFilter : FILTERS ");
        HttpServletResponse httpServletResponse = (HttpServletResponse)res;
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        super.doFilter(req, res, chain);
    }
}