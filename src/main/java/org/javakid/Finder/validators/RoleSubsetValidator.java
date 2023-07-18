package org.javakid.Finder.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.javakid.Finder.annotations.RoleSubset;
import org.javakid.Finder.enums.Role;

import java.util.Arrays;

public class RoleSubsetValidator implements ConstraintValidator<RoleSubset, Role> {

    private Role[] roles;

    @Override
    public void initialize(RoleSubset constraintAnnotation) {
        this.roles = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(Role value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(roles).contains(value);
    }
}
