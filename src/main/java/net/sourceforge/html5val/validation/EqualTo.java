package net.sourceforge.html5val.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validation annotation to compare an element with another.
 * Now it is only used in client jQuery validation, not processed by server Hibernate-validator-
 */
// FIXME: implement a Validator for this constraint.
// http://stackoverflow.com/questions/1972933/cross-field-validation-with-hibernate-validator-jsr-303
// @Constraint(validatedBy = EqualToValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EqualTo {
    String target();
    String message() default "Selected element is not equal to target element";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
