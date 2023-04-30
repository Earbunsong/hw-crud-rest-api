package com.example.mbanking.api.user.web;

import com.example.mbanking.api.user.UserService;
import com.example.mbanking.base.BaseRest;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PutMapping("/{id}")
    public BaseRest<?> updateUserById(@PathVariable("id") Integer id, @RequestBody UpdateUserDto updateUserDto) {
        UserDto userDto = userService.updateUserById(id, updateUserDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been updated successfully .")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }


    @PutMapping("/{id}/update-is-deleted")
    public BaseRest<?> updateIsDeletedStatusById(@PathVariable Integer id, @RequestBody IsDeletedDto dto) {
        Integer deletedId = userService.updateIsDeletedStatusById(id, dto.status());
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been deleted successfully .")
                .timestamp(LocalDateTime.now())
                .data(deletedId)
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseRest<?> deleteUserById(@PathVariable Integer id) {
        Integer deletedId = userService.deletedUserById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been deleted successfully .")
                .timestamp(LocalDateTime.now())
                .data(deletedId)
                .build();
    }

    @GetMapping
    public BaseRest<?> findAllUsers(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(name = "limit", required = false, defaultValue = "20") int limit,
                                    @RequestParam(name = "name", required = false, defaultValue = "") String name) {
        PageInfo<UserDto> userDtoPageInfo = userService.findAllUsers(page, limit, name);
        return BaseRest.builder()
                .status(true)
                .code(200)
                .message("User have been successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDtoPageInfo)
                .build();
    }


    @PostMapping("")
    public BaseRest<?> createNewUser(@RequestBody @Valid CreateUserDto createUserDto) {
        UserDto userDto = userService.createNewUser(createUserDto);
        return BaseRest.builder()
                .status(true)
                .code(200)
                .message("User have been successfully.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }

    @GetMapping("/{id}")
    BaseRest<?> findById(@PathVariable("id") Integer id) {
        UserDto userDto = userService.findUserById(id);
        return BaseRest.builder()
                .status(true)
                .code(200)
                .message("User have been found.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }

    @GetMapping("/{studentCardId}/student-card-id")
    BaseRest<?> findStudentCardId(@PathVariable("studentCardId") String studentCardId) {
        UserDto userDto = userService.selectByStudentCardId(studentCardId);
        return BaseRest.builder()
                .status(true)
                .code(200)
                .message("User have been found.")
                .timestamp(LocalDateTime.now())
                .data(userDto)
                .build();
    }
}
