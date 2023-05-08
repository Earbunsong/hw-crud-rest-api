package com.example.mbanking.api.account;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface AccountMapper {
     @SelectProvider(type = AccountProvider.class,method = "buildSelectAccByIdSql")
     @Result(column = "account_no",property = "accountNo")
     @Result(column = "account_name",property = "accountName")
     @Result(column = "phone_number",property = "phoneNumber")
     @Result(column = "transfer_limit",property = "transferLimit")
     @Result(column = "account_type",property = "accountType")
     Optional<Account> selectAccountById(@Param("id")Integer id);


}
