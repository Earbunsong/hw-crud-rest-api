package com.example.mbanking.api.accounttype;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {
    public final AccountTypeMapper accountTypeMapper;
    private final AccountTypeMapStruct accountTypeMapStruct;
    @Override
    public List<AccountTypeDto> findAll() {
        List<AccountType> accountTypes = accountTypeMapper.select();
        return accountTypeMapStruct.toDtoList(accountTypes);
    }

    @Override
    public AccountTypeDto addAccountType(AccountTypeDto accountTypeDto) {
        AccountType accountType = accountTypeMapStruct.toAccountTypes(accountTypeDto);
        accountTypeMapper.insert(accountType);
        return accountTypeMapStruct.toDto(accountType);
    }

    @Override
    public AccountTypeDto findById(Integer id) {
        AccountType accountType = accountTypeMapper.selectById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("AccountType Id with %d not found !!",id)
                )
        );
        return accountTypeMapStruct.toDto(accountType);
    }

    @Override
    public AccountTypeDto update( Integer id,AccountTypeDto accountTypeDto ) {
        Boolean existById = accountTypeMapper.existId(id);
        AccountType accountType = accountTypeMapStruct.toAccountTypes(accountTypeDto);
        if (existById) {
            accountTypeMapper.update(id ,accountType);
            return this.findById(id);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("AccountType Id with %d not found !!",id)
        );
    }

    @Override
    public Integer deletedById(Integer id) {
        Boolean existById = accountTypeMapper.existId(id);
        if (existById){
            accountTypeMapper.delete(id);
            return id;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("AccountType Id with %d not found !!",id)
        );
    }
}
