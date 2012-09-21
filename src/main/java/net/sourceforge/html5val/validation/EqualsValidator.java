package net.sourceforge.html5val.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualsValidator implements ConstraintValidator<Equals, Object> {

    private Equals annotation;

    public void initialize(Equals annotation) {
        this.annotation = annotation;
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {
        return EqualsValidatorHelper.with(object, annotation).performValidation();
    }
}