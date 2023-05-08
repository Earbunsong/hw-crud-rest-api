package com.example.mbanking.api.accounttype;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapStruct {
        AccountTypeDto toDto(AccountType accountType);
        List<AccountTypeDto> toDtoList(List<AccountType> accountTypes);
        AccountType toAccountTypes(AccountTypeDto accountTypeDto);
}
