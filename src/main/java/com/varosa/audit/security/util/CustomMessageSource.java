package com.varosa.audit.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageSource {

    private final MessageSource messageSource;

    @Autowired
    public CustomMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String code) {
        return messageSource.getMessage(code,null,null);
    }

    public String get(String code,Object... objects) {
        return messageSource.getMessage(code,objects,null);
    }

}
