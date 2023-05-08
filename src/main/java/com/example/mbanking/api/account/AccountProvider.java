package com.example.mbanking.api.account;

import org.apache.ibatis.jdbc.SQL;

public class AccountProvider {
      public String buildSelectAccByIdSql(){
          return new SQL(){{
              SELECT("*");
              FROM("accounts");
              WHERE("id = #{id}");
          }}.toString();
      }
}
