package com.workspaceit.dccpos.config.security.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//@RestController
public class LogoutEndpoint {

    private InMemoryTokenStore tokenStore;


    //@Autowired
    //@Qualifier("tokenStore")
    public void setTokenStore(InMemoryTokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/logout")
    @ResponseBody
    public void revokeToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.contains("Bearer")){
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
    }
}
