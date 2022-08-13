package io.github.zam0k.HolyHealth.validation.constrainValidation;

import io.github.zam0k.HolyHealth.validation.EnumPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EnumPatternValidator implements ConstraintValidator<EnumPattern, Enum> {
    private Pattern pattern;

    @Override
    public void initialize(EnumPattern constraintAnnotation) {
        try {
            pattern = Pattern.compile(constraintAnnotation.regexp());
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Regex invalid", e);
        }
    }

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        Matcher m = pattern.matcher(value.name());
        return m.matches();
    }
}
