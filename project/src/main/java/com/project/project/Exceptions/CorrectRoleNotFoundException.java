package com.project.project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class CorrectRoleNotFoundException extends RuntimeException {
   public CorrectRoleNotFoundException(String message){
        super(message);

    }
}
