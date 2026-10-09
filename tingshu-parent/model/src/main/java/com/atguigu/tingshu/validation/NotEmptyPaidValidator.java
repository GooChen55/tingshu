package com.atguigu.tingshu.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class NotEmptyPaidValidator implements ConstraintValidator<NotEmptyPaid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        String[] array = value.split("_");
        if(array.length == 0 || "0101".equals(array[0])) return true;
        if(array.length != 2) return false;
        return true;
    }
}
