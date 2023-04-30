package com.example.mbanking.api.accounttype;

import com.example.mbanking.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-types")
public class AccountTypeController {
    private final AccountTypeService accountTypeService;
    @GetMapping
    public BaseRest<?> findAll(){
        var accountTypeDtoList = accountTypeService.findAll();
        return BaseRest.builder()
                .status(true)
                .code(200)
                .message("Account type have been found")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDtoList)
                .build();
    }
    @GetMapping("/{id}")
    public BaseRest<?> findById(@PathVariable ("id") Integer id){
        AccountTypeDto accountTypeDto = accountTypeService.findById(id);
        return BaseRest.builder()
                .status(true)
                .code(200)
                .message("Account type have been found ..!!")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDto)
                .build();
    }
    @PutMapping("/{id}")
    public BaseRest<?> updateById(@PathVariable("id") Integer id ,@RequestBody AccountTypeDto accountTypeDto){
        AccountTypeDto accountTypeDtoResult = accountTypeService.update(id, accountTypeDto);
        return BaseRest.builder()
                .status(true)
                .code(200)
                .message("Account type have  been update success ..!!")
                .timestamp(LocalDateTime.now())
                .data(accountTypeDtoResult)
                .build();
    }
    @DeleteMapping("/{id}")
    public BaseRest<?> deleteById(@PathVariable("id") Integer id){
        Integer idResult = accountTypeService.deletedById(id);
        return BaseRest.builder()
                .status(true)
                .code(200)
                .message("Account type have  been delete success ..!!")
                .timestamp(LocalDateTime.now())
                .data(idResult)
                .build();
    }
}
