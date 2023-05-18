package com.example.mbanking.api.user.validator.password;

import com.example.mbanking.api.user.validator.email.EmailUniqueConstrainValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PasswordConstrainValidator.class )
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface Password {

    String message() default "Your password is so week!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
