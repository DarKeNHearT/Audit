package com.varosa.audit.security;


import com.varosa.audit.error.util.AESEncryptDecrypt;
import com.varosa.audit.model.User;
import com.varosa.audit.security.util.CustomUserDetails;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        User currentUser = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getUser();
        final Map<String, Object> additionalInfo = new HashMap<>();
        Hibernate.initialize(currentUser.getId());
        Long userId = currentUser.getId();

        /**
         * All necessary data here
         */
        additionalInfo.put("userid", AESEncryptDecrypt.encryptLong(userId));
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
