package msa.pseudo.lotto.userserivce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity duplicateEntry(SQLIntegrityConstraintViolationException SQLIntegrityConstraintViolationException) {
        // unique 제약 조건 위반(중복 ID)
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity noHandler(NoHandlerFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity defaultHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
