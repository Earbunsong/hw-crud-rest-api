package com.example.mbanking.api.account.wep;

public record AccountDto(String accountNo,
                         String accountName,
                         String profile,
                         Integer pin,
                         String password,
                         String phoneNumber,
                         Integer transferLimit,
                         Integer accountType) {
}
