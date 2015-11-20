package com.codeaim.statuswarden.api.controller;

import com.codeaim.statuswarden.common.exception.AlreadyExistsException;
import com.codeaim.statuswarden.common.exception.MethodNotAllowedException;
import com.codeaim.statuswarden.common.exception.NotFoundException;
import com.codeaim.statuswarden.common.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractController
{
    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(
        MethodNotAllowedException e)
    {
        return handleGenericException(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(
        UnauthorizedException e)
    {
        return handleGenericException(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExistsException(
        AlreadyExistsException e)
    {
        return handleGenericException(e, HttpStatus.CONFLICT);
    }


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
