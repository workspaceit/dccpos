package com.workspaceit.pos.config.security;

import com.workspaceit.pos.config.Environment;
import com.workspaceit.pos.constant.ACCESS_ROLE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


@Configuration
public class Oauth2Configuration {

    private static InMemoryTokenStore tokenStore = new InMemoryTokenStore();
    private static final String SERVER_RESOURCE_ID = "oauth2-server";
    private static final boolean ENABLE_OAUTH = false;

    @Configuration
    @EnableResourceServer
    @EnableGlobalMethodSecurity(securedEnabled = true)
    protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.tokenStore(tokenStore).resourceId(SERVER_RESOURCE_ID);
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {

            String uri = (ENABLE_OAUTH)?"/auth/api/**":"/dummy-auth/api/**";
            http.antMatcher(uri)
                    .authorizeRequests()
                    .antMatchers(uri).authenticated()
                    .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler()).and().cors();

            //.antMatchers("/auth/api/**").access("hasRole('"+ ACCESS_ROLE.POS_OPERATOR.name()+"')")
        }


    }
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthServer extends AuthorizationServerConfigurerAdapter {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        private AuthenticationManager authenticationManager;
        private UserDetailsService authCredentialDetailsService;
        private Environment environment;



        @Autowired
        @Qualifier("authenticationManagerBean")
        public void setAuthenticationManager(AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
        }

        @Autowired
        public void setAuthCredentialDetailsService(UserDetailsService authCredentialDetailsService) {
            this.authCredentialDetailsService = authCredentialDetailsService;
        }

        @Autowired
        public void setEnvironment(Environment environment) {
            this.environment = environment;
        }


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient(this.environment.getOauthClientId())
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                    .authorities("ROLE_"+ACCESS_ROLE.ADMIN.name())
                    .scopes("read", "write", "trust")
                    .secret(passwordEncoder.encode(this.environment.getOauthSecret()))
                    .accessTokenValiditySeconds(60*60*24)
                    .resourceIds(SERVER_RESOURCE_ID);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)
                    .userDetailsService(authCredentialDetailsService);

        }
        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.allowFormAuthenticationForClients()
                    .passwordEncoder(passwordEncoder);
        }


    }

}