package com.example.mbanking.api.accounttype;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeDto> findAll();
    AccountTypeDto addAccountType(AccountTypeDto accountTypeDto);
    AccountTypeDto findById(Integer id);
    AccountTypeDto update( Integer id,AccountTypeDto accountTypeDto );
    Integer deletedById(Integer id);
}
