package com.example.mbanking.api.user;

import com.example.mbanking.api.user.web.CreateUserDto;
import com.example.mbanking.api.user.web.UpdateUserDto;
import com.example.mbanking.api.user.web.UserDto;
import com.github.pagehelper.PageInfo;

public interface UserService {
    UserDto createNewUser(CreateUserDto createUserDto);
    UserDto findUserById(Integer id);
    PageInfo<UserDto> findAllUsers(int page ,int limit,String name);
    Integer deletedUserById(Integer  id);
    Integer updateIsDeletedStatusById(Integer id ,boolean status);
    UserDto updateUserById(Integer id, UpdateUserDto updateUserDto);
    UserDto selectByStudentCardId(String name);
}
