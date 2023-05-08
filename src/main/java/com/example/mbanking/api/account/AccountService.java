package com.example.mbanking.api.account;

import com.example.mbanking.api.account.wep.AccountDto;

public interface AccountService {
  AccountDto findAccountById( Integer id);
}
