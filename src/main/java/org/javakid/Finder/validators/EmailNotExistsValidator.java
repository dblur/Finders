package org.javakid.Finder.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.javakid.Finder.annotations.EmailNotExists;
import org.javakid.Finder.services.crud.UserCrudService;

@RequiredArgsConstructor
public class EmailNotExistsValidator implements ConstraintValidator<EmailNotExists, String> {

    private final UserCrudService userCrudService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userCrudService.existsUserByEmail(value);
    }
}
