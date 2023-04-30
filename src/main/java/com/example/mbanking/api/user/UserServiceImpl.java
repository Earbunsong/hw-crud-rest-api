package com.example.mbanking.api.user;

import com.example.mbanking.api.user.web.CreateUserDto;
import com.example.mbanking.api.user.web.UpdateUserDto;
import com.example.mbanking.api.user.web.UserDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserMapStruct userMapStruct;
    @Override
    public UserDto createNewUser(CreateUserDto createUserDto) {
         User user = userMapStruct.CreateUserDtoToUser( createUserDto);
         userMapper.insert(user);
         log.info("User ={}",user.getId());
        return userMapStruct.userToUserDto(user);

    }

    @Override
    public UserDto findUserById(Integer id) {
        User user = userMapper.selectById(id).orElseThrow(( )->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with %d is not found" ,id)));
        System.out.println(user);
        return userMapStruct.userToUserDto(user);
    }

    @Override
    public PageInfo<UserDto> findAllUsers(int page, int limit,String name) {
        if (name.equals("")){
            PageInfo<User> userPageInfo = PageHelper.startPage(page,limit)
                    .doSelectPageInfo(userMapper::select);
            return userMapStruct.userpageInfoToUserDtopageInfo(userPageInfo);
        }
        PageInfo<User> userPageInfo = PageHelper.startPage(page,limit)
                .doSelectPageInfo(() -> userMapper.selectByName(name));
        return userMapStruct.userpageInfoToUserDtopageInfo(userPageInfo);
    }

    @Override
    public Integer deletedUserById(Integer id) {
        boolean idExisted = userMapper.existById(id);
        if (idExisted){
            //DELETED
            userMapper.deletedById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with %d is not found ",id));
    }

    @Override
    public Integer updateIsDeletedStatusById(Integer id, boolean status) {
        boolean isExited = userMapper.existById(id);
        if (isExited){
            userMapper.updateIsDeletedById(id,status);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User with %d is not found ",id));    }

    @Override
    public UserDto updateUserById(Integer id, UpdateUserDto updateUserDto) {
         if (userMapper.existById(id)){
//            User user = userMapper.updateIsDeletedById(updateUserDto);
//            user.setId(id);
//            UserMapper.updateByid(user);
             return this.findUserById(id);
         }
         throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                 String.format("User with %d is not found ",id));
    }

    @Override
    public UserDto selectByStudentCardId(String studentCardId) {
        User user = userMapper.selectByStudentCardId(studentCardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with %s is not found ",studentCardId)
                ));

        return userMapStruct.userToUserDto(user) ;
    }

}
