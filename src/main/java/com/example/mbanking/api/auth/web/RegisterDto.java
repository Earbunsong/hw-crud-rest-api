package com.example.mbanking.api.auth.web;

import com.example.mbanking.api.user.validator.email.EmailUnique;
import com.example.mbanking.api.user.validator.password.Password;
import com.example.mbanking.api.user.validator.password.PasswordMatch;
import com.example.mbanking.api.user.validator.role.RoleIdConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
@PasswordMatch(password = "password",confirmedPassword = "confirmedPassword")
public record RegisterDto(
        @NotBlank(message = "Email is required")
        @Email
        @EmailUnique
        String email,

        @NotBlank(message = "Password id required")
        @Password
        String password,
        @NotBlank(message = "Confirmed password is required")
        @Password
        String confirmedPassword,
        @NotNull(message = "Roles are required!")
        @RoleIdConstraint
        List<Integer> roleIds) {

}
