package net.sourceforge.html5val.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import static net.sourceforge.html5val.constraints.EqualsValidatorHelper.validate;

public class EqualsValidator implements ConstraintValidator<Equals, Object> {

    private Equals annotation;

    public void initialize(Equals annotation) {
        this.annotation = annotation;
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {
        return validate(object, annotation);
    }
}
