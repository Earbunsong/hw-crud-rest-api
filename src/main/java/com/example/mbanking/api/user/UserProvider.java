package com.example.mbanking.api.user;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    private static final String tableName = "users";
    public String buildUpdateByIdSql(){
        return new SQL(){{
            UPDATE(tableName);
            SET("name = #{u.name}");
            SET("gender = #{u.gender}");
            WHERE("id = #{u.id}" );
        }}.toString();
    }
    public String buildUpdateDeletedByIdSql(){
        return new SQL(){{
            UPDATE(tableName);
            SET("is_deleted = #{status}");
            WHERE("id = #{id}");
        }}.toString();
    }
    public String buildDeleteByIdSql (){
        return new SQL(){{
            DELETE_FROM(tableName);
            WHERE("id = #{id}");
        }}.toString();
    }
    public String buildSelectAll(){
        return new SQL(){{
            SELECT("*");
            FROM(tableName );
            WHERE("is_deleted = FALSE ");
            ORDER_BY("id DESC");
        }}.toString();
    }

    public String buildInsertSql(@Param("u") User user){
        return new SQL(){{
            INSERT_INTO(tableName);
            VALUES("name","#{u.name}");
            VALUES("gender","#{u.gender}");
            VALUES("one_signal_id","#{u.oneSignalId}");
            VALUES("student_card_id","#{u.studentCardId}");
            VALUES("is_student","#{u.isStudent}");
            VALUES("is_deleted","FALSE");
        }}.toString();
    }
    public String buildSelectById(){
        return new SQL(){{
            SELECT("*");
            FROM("users");
            WHERE("id = #{id}" ,"is_deleted = FALSE");

        }}.toString();
    }
    public String buildSelectByName(){
        return new SQL(){{
            SELECT("*");
            FROM("account_types");
            WHERE("name LIKE '%' ||  #{name}  || '%'" , "is_deleted=FALSE");
        }}.toString();
    }
    public String buildSelectByStudentCardId(){
        return new SQL(){{
            SELECT("*");
            FROM("account_types");
            WHERE("student_card_id=#{studentCardId}" , "is_deleted=FALSE");
        }}.toString();
    }
}
