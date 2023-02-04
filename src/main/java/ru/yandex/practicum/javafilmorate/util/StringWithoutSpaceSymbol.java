package ru.yandex.practicum.javafilmorate.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringValidator.class)
@Documented
public @interface StringWithoutSpaceSymbol {

    String message() default "{Это строковое поле не может содержать пробелы}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
