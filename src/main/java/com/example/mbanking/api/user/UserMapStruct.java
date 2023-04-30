package com.example.mbanking.api.user;

import com.example.mbanking.api.user.web.CreateUserDto;
import com.example.mbanking.api.user.web.UpdateUserDto;
import com.example.mbanking.api.user.web.UserDto;
import com.github.pagehelper.PageInfo;
import org.apache.tomcat.util.buf.UEncoder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
    User CreateUserDtoToUser(CreateUserDto createUserDto);
    User updateUserDtoToUser(UpdateUserDto updateUserDto);
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
    PageInfo<UserDto> userpageInfoToUserDtopageInfo(PageInfo<User> userPageInfo);

}
