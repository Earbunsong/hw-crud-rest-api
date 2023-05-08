package com.example.mbanking.api.account.wep;

import com.example.mbanking.api.account.AccountService;
import com.example.mbanking.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account")
public class AccountRestController {

    private final AccountService accountService;
    @GetMapping("/{id}")
    public BaseRest<?> findAccountById(@PathVariable Integer id){
        AccountDto accountDto = accountService.findAccountById(id);
        return BaseRest.builder()
                .status(true)
                .code(200)
                .message("Account  have been found")
                .timestamp(LocalDateTime.now())
                .data(accountDto)
                .build();
    }
}
