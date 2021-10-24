package com.varosa.audit.security.util;

import com.varosa.audit.error.util.AESEncryptDecrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Map;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    private final TokenStore tokenStore;

    public JpaAuditingConfiguration(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new AuditorAware<Long>() {
            @Override
            public Optional<Long> getCurrentAuditor() {
                if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    OAuth2AuthenticationDetails auth2AuthenticationDetails = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
                    Map<String, Object> details = tokenStore.readAccessToken(auth2AuthenticationDetails.getTokenValue()).getAdditionalInformation();
                    Long userid = AESEncryptDecrypt.decryptLong((String) details.get("userid"));
                    return Optional.of(userid);
                } else {
                    return Optional.of(0L);
                }
            }
        };
    }
}
