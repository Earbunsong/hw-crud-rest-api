package com.example.mbanking.execption;

import com.example.mbanking.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@RestControllerAdvice
public class ApiException {

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(NoSuchElementException.class)
//    public BaseError<?> handleNoSuchFileException(NoSuchElementException e){
//        return BaseError.builder()
//                .message("something when worng ......please check !!!!!!!")
//                .status(false)
//                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .errors(e.getReason())
//                .build();


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseError<?> handleValidationException(MethodArgumentNotValidException e ){
        List<Map<String, String>> errors = new ArrayList<>();
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
//                .message(e.getMessage())
                .message("Validation is error , please check detail messages!")
                .errors(errors)
                .build();


    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResponseStatusException.class)
    public BaseError<?> handleServiceImpl(ResponseStatusException e){
        return BaseError.builder()
                .message("something when wording ......please check !!!!!!!")
                .status(false)
                .code(e.getStatusCode().value())
                .errors(e.getReason())
                .build();
    }
}
