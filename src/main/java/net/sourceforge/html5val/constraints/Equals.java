package net.sourceforge.html5val.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

/**
 * Validation annotation to compare a field with another.
 *
 * The comparison is performed using the equals() method.
 *
 * If both fields are null, the validation is passed.
 */
@Constraint(validatedBy = EqualsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Equals {
    String property1();
    String property2();
    String message() default "Fields are not equal";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
