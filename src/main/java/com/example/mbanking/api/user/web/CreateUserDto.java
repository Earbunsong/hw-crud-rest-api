package com.example.mbanking.api.user.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateUserDto(@NotBlank(message = "Name is required!") String name,
                           @NotBlank String gender,
                            String oneSignalId,
                            String studentCardId,
                            @NotNull(message = "You have to confirm, are you s student!") boolean isStudent) {
}
