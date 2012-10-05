package unit.net.sourceforge.html5val.performers;

import javax.validation.constraints.Digits;
import org.hibernate.validator.internal.constraintvalidators.DigitsValidatorForCharSequence;
import static org.junit.Assert.*;
import static unit.util.RegexpMatcher.matchesRegexp;
import static unit.util.RegexpMismatcher.doesNotMatchRegexp;

/**
 * Performs both Hibernate-validator and Regexp-based validations.
 */
class ValidationChecker {

    private DigitsValidatorForCharSequence validator;
    private String pattern;
    private Digits digitsAnnotation;

    public ValidationChecker(Digits digitsAnnotation, String pattern) {
        this.pattern = pattern;
        this.digitsAnnotation = digitsAnnotation;
        validator = new DigitsValidatorForCharSequence();
        validator.initialize(digitsAnnotation);
    }

    public void isValid(String value) {
        assertThat(value, matchesRegexp(pattern));
        assertTrue(String.format("%s is not valid for @Digits(%s, %s)", value, digitsAnnotation.integer(), digitsAnnotation.fraction()), validator.isValid(value, null));
    }

    public void isNotValid(String value) {
        assertThat(value, doesNotMatchRegexp(pattern));
        assertFalse(String.format("%s should not be valid for @Digits(%s, %s)", value, digitsAnnotation.integer(), digitsAnnotation.fraction()), validator.isValid(value, null));
    }
}
