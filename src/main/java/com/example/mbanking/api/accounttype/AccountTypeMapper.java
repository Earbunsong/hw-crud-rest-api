package com.example.mbanking.api.accounttype;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AccountTypeMapper {
//     @Select("SELECT * FROM account_types")
     @SelectProvider(type =  AccountTypeProvider.class,method = "buildSelectSql")
     List<AccountType> select();
     @SelectProvider(type = AccountTypeProvider.class,method = "buildSelectIdSql")
     Optional<AccountType> selectById(@Param("id")Integer id);

     @InsertProvider(type = AccountTypeProvider.class,method = "buildInsertSql")
     void insert(@Param("t")AccountType accountType);
     @Select("SELECT EXIST (SELECT * FROM account_types WHERE id = #{id})  ")
     Boolean existId(@Param("id")Integer id);

     @UpdateProvider(type = AccountTypeProvider.class,method = "buildUpdateSql")
     void update(@Param("id") Integer id,@Param("t")AccountType accountType);
     @DeleteProvider(type = AccountTypeProvider.class,method = "buildDeleteById")
     void delete(@Param("id") Integer id);


}
