package com.example.mbanking.api.account;

import com.example.mbanking.api.account.wep.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapStruct {
    AccountDto accToDto(Account account);
}
