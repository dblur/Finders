package org.javakid.Finder.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.javakid.Finder.validators.PasswordValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    String message() default
            """
            Password must contain at least one digit [0-9].
            Password must contain at least one lowercase Latin character [a-z].
            Password must contain at least one uppercase Latin character [A-Z].
            Password must contain at least one special character like ! @ # & ( ).
            Password must contain a length of at least 8 characters and a maximum of 20 characters.
            """;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
