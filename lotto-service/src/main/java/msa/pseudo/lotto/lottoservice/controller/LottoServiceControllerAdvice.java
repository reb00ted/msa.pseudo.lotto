package msa.pseudo.lotto.lottoservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LottoServiceControllerAdvice {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity hello(Exception e) {
        System.out.println("예외 처리");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
