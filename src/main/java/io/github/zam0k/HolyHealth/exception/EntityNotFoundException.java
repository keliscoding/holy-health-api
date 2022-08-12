package io.github.zam0k.HolyHealth.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException() {

        super("Id is incorrect or entity cannot be found.");
    }
}
