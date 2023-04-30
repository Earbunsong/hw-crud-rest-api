package com.example.mbanking.api.user;

import com.example.mbanking.api.user.web.UpdateUserDto;
import lombok.Builder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface UserMapper {
    @InsertProvider(type = UserProvider.class,method = "buildInsertSql")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insert(@Param("u") User user);

    @SelectProvider(type = UserProvider.class ,method = "buildSelectById")
    @Results(id = "useResultMap",value = {
            @Result(column = "student_card_id" , property = "studentCardId"),
            @Result(column = "is_student",property = "isStudent")
    })
    Optional<User> selectById(@Param("id") Integer id);

    @SelectProvider(type = UserProvider.class,method = "buildSelectAll")
    @ResultMap("useResultMap")
    List<User> select();


    @Select("SELECT EXISTS(SELECT * FROM users WHERE id = #{id})")
    boolean existById(@Param("id") Integer id);

    @SelectProvider(type = UserProvider.class,method = "buildDeleteByIdSql")
    void deletedById(@Param("id")Integer id);

    @UpdateProvider(type = UserProvider.class,method = "buildUpdateDeletedByIdSql")
    void updateIsDeletedById(@Param("id") Integer id, @Param("status") boolean status);

    @UpdateProvider(type = UserProvider.class,method = "buildUpdateByIdSql" )
    void UpdateById(@Param("u") User user);
    @SelectProvider(type = UserProvider.class,method = "buildSelectByName")
    List<User> selectByName(@Param("name") String name);
    @SelectProvider(type = UserProvider.class,method = "buildSelectByStudentCardId")
    Optional<User> selectByStudentCardId(@Param("studentCardId") String studentCardId);
}


