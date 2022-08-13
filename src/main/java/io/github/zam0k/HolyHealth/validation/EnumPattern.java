package io.github.zam0k.HolyHealth.validation;


import io.github.zam0k.HolyHealth.validation.constrainValidation.EnumPatternValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = EnumPatternValidator.class)
public @interface EnumPattern {
    String regexp();
    String message() default "Field must match \"{regexp}\".";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
