package com.example.demo.error;


import com.example.demo.error.util.CustomOauthExceptionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.util.Map;

@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
    public CustomOauthException(String msg) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return super.getOAuth2ErrorCode();
    }

    @Override
    public int getHttpErrorCode() {
        return super.getHttpErrorCode();
    }

    @Override
    public Map<String, String> getAdditionalInformation() {
        return super.getAdditionalInformation();
    }

    @Override
    public void addAdditionalInformation(String key, String value) {
        super.addAdditionalInformation(key, value);
    }
}
