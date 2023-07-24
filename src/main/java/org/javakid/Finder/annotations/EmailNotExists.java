package org.javakid.Finder.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.javakid.Finder.validators.EmailNotExistsValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EmailNotExistsValidator.class)
public @interface EmailNotExists {

    String message() default "User with such email is already registered";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
