package ru.yandex.practicum.javafilmorate.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class StringValidator implements ConstraintValidator<StringWithoutSpaceSymbol, String> {

    public static boolean isNullOrEmpty(String stringToValidate) {

        return stringToValidate == null || stringToValidate.isEmpty();
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return !login.contains(" ");
    }
}
