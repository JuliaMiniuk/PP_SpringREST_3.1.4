package com.learning.SpringSecurity312.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(NoSuchUserException exception) {
        UserIncorrectData userIncorrectData = new UserIncorrectData();
        userIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(userIncorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleException(Exception exception) {
        UserIncorrectData userIncorrectData = new UserIncorrectData();
        userIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(userIncorrectData, HttpStatus.BAD_REQUEST);
    }
}
