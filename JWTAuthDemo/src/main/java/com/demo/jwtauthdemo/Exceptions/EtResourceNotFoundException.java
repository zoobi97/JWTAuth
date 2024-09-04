package com.demo.jwtauthdemo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EtResourceNotFoundException extends RuntimeException{

    public EtResourceNotFoundException(String message) {
        super(message);
    }
}
