package org.javakid.Finder.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.javakid.Finder.enums.Role;
import org.javakid.Finder.validators.RoleSubsetValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = RoleSubsetValidator.class)
public @interface RoleSubset {

    Role[] anyOf();
    String message() default "must be any of {anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
