package com.example.mbanking.api.user.validator.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchConstrainValidator implements ConstraintValidator<PasswordMatch,Object> {

    private String password;
    private String confirmedPassword;
    private String message;
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
        this.password = constraintAnnotation.password();
        this.confirmedPassword= constraintAnnotation.confirmedPassword();
        this.message= constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
        Object confirmedPasswordValue = new BeanWrapperImpl(value).getPropertyValue(confirmedPassword);
        boolean isValid = false;

        if (passwordValue != null){
            isValid = passwordValue.equals(confirmedPasswordValue);
        }

        if (!isValid){
            //sending one message each message time failed validation.
            context.disableDefaultConstraintViolation();

            //build b
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(password)
                    .addConstraintViolation();

            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(confirmedPassword)
                    .addConstraintViolation();




        }

        return isValid;
    }
}
