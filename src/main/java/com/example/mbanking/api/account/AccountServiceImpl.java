package com.example.mbanking.api.account;

import com.example.mbanking.api.account.wep.AccountDto;
import com.example.mbanking.api.accounttype.AccountTypeMapStruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountMapStruct accountMapStruct;

    @Override
    public AccountDto findAccountById(Integer id) {
        Account account = accountMapper.selectAccountById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Account with %d is not found",id)));
        return accountMapStruct.accToDto(account);
    }
}
