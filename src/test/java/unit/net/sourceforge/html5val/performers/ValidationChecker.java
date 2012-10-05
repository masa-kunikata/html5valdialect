package unit.net.sourceforge.html5val.performers;

import java.lang.annotation.Annotation;
import javax.validation.ConstraintValidator;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.internal.constraintvalidators.DigitsValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.LengthValidator;
import org.hibernate.validator.internal.constraintvalidators.SizeValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.URLValidator;
import static org.junit.Assert.*;
import static unit.util.RegexpMatcher.matchesRegexp;
import static unit.util.RegexpMismatcher.doesNotMatchRegexp;

/**
 * Performs both Hibernate-validator and Regexp-based validations.
 */
class ValidationChecker {

    private ConstraintValidator validator;
    private String pattern;
    private Annotation annotation;

    public ValidationChecker(Annotation annotation, String pattern) {
        this.pattern = pattern;
        this.annotation = annotation;
        validator = validatorFor(annotation);
        validator.initialize(annotation);
    }

    private ConstraintValidator validatorFor(Annotation annotation) {
        if (annotation instanceof Digits) {
            return new DigitsValidatorForCharSequence();
        } else if (annotation instanceof URL) {
            return new URLValidator();
        } else if (annotation instanceof Length) {
            return new LengthValidator();
        } else if (annotation instanceof Size) {
            return new SizeValidatorForCharSequence();
        } else {
            throw new IllegalArgumentException("ConstraintValidator unknown");
        }
    }

    public void isValid(String value) {
        assertThat(value, matchesRegexp(pattern));
        assertTrue(String.format("%s is not valid for annotation %s", value, annotation.toString()), validator.isValid(value, null));
    }

    public void isNotValid(String value) {
        assertThat(value, doesNotMatchRegexp(pattern));
        assertFalse(String.format("%s should not be valid for annotation %s", value, annotation.toString()), validator.isValid(value, null));
    }
}
