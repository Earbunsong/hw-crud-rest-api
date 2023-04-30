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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@RestControllerAdvice
public class ApiException {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseError<?> handleValidationException(MethodArgumentNotValidException e ){
        List<Map<String, String>> errors = new ArrayList<>();


        for (FieldError error :  e.getFieldErrors()) {
            Map<String ,String> errorDetails = new HashMap<>();
            errorDetails.put("name", error.getField());
            errorDetails.put("message", error.getDefaultMessage());
            errors.add(errorDetails);
        }
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
                .message("something when worng ......please check !!!!!!!")
                .status(false)
                .code(e.getStatusCode().value())
                .errors(e.getReason())
                .build();
    }
}