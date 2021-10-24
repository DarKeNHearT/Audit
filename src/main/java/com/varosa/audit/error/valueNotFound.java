package com.varosa.audit.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class valueNotFound extends Exception {
    public valueNotFound(String message){
        super(message);
    }
}
