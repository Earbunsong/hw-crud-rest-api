package com.example.mbanking.api.user.validator.password;

import com.example.mbanking.api.user.validator.email.EmailUniqueConstrainValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PasswordMatchConstrainValidator.class )
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE,ElementType.METHOD})
public @interface PasswordMatch {
    String message() default "Your password is wrong!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    String password();
    String confirmedPassword();

    @Retention(RetentionPolicy.RUNTIME)
    @Target({TYPE})
    @interface List {
        PasswordMatch[] value();
    }

}
