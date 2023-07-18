package org.javakid.Finder.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.javakid.Finder.annotations.SexSubset;
import org.javakid.Finder.enums.Sex;

import java.util.Arrays;

public class SexSubsetValidator implements ConstraintValidator<SexSubset, Sex> {

    private Sex[] genders;

    @Override
    public void initialize(SexSubset constraintAnnotation) {
        this.genders = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(Sex value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(genders).contains(value);
    }
}
