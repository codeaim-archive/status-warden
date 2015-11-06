package com.codeaim.statuswarden.api.controller;

import com.codeaim.statuswarden.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractController
{
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(
        NotFoundException e)
    {
        return handleGenericException(e, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<?> handleGenericException(
        Exception e,
        HttpStatus status)
    {
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
