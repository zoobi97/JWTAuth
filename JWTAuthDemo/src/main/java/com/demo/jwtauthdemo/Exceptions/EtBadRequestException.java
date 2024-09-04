package com.demo.jwtauthdemo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EtBadRequestException extends RuntimeException {
    public EtBadRequestException(String message) {
        super(message);
    }
}
