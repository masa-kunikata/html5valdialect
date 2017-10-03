package net.sourceforge.html5val.util;

import static net.sourceforge.html5val.util.RegexpMatcher.*;
import static net.sourceforge.html5val.util.RegexpMismatcher.*;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.internal.constraintvalidators.DigitsValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.LengthValidator;
import org.hibernate.validator.internal.constraintvalidators.SizeValidatorForCharSequence;
import org.hibernate.validator.internal.constraintvalidators.URLValidator;

/**
 * Performs both Hibernate-validator and Regexp-based validations.
 */
public class ValidationChecker {

    private ConstraintValidator<Annotation, CharSequence> validator;
    private String pattern;
    private Annotation annotation;

    public ValidationChecker(Annotation annotation, String pattern) {
        this.pattern = pattern;
        this.annotation = annotation;
        validator = ConstraintValidators.validatorFor(annotation);
        validator.initialize(annotation);
    }

    private enum ConstraintValidators implements ConstraintValidator<Annotation, CharSequence>{
    	DIGITS{
        	private DigitsValidatorForCharSequence vdt = new DigitsValidatorForCharSequence();
			@Override
			public void initialize(Annotation constraintAnnotation) {
				vdt.initialize((Digits)constraintAnnotation);
			}

			@Override
			public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
				return vdt.isValid(value, context);
			}
    	},
    	URL{
        	private URLValidator vdt = new URLValidator();
			@Override
			public void initialize(Annotation constraintAnnotation) {
				vdt.initialize((URL)constraintAnnotation);
			}

			@Override
			public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
				return vdt.isValid(value, context);
			}

    	},
    	LENGTH{
        	private LengthValidator vdt = new LengthValidator();
			@Override
			public void initialize(Annotation constraintAnnotation) {
				vdt.initialize((Length)constraintAnnotation);
			}

			@Override
			public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
				return vdt.isValid(value, context);
			}
    	},
    	SIZE{
        	private SizeValidatorForCharSequence vdt = new SizeValidatorForCharSequence();
			@Override
			public void initialize(Annotation constraintAnnotation) {
				vdt.initialize((Size)constraintAnnotation);
			}

			@Override
			public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
				return vdt.isValid(value, context);
			}
    	},
    	;
    	static ConstraintValidator<Annotation, CharSequence> validatorFor(Annotation annotation){
            if (annotation instanceof Digits) return DIGITS;
            if (annotation instanceof URL)    return URL;
            if (annotation instanceof Length) return LENGTH;
            if (annotation instanceof Size)   return SIZE;

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
