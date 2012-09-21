package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import net.sourceforge.html5val.validation.Equals;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Translate JSR-303 annotations into jquery-validator-plugin rules.
 * We also suport Hibernate @NotEmpty and @Email annotations.
 */
public class Jsr303toJQueryBridge {

    /**
     * Generates a jquery-validator-plugin rule for the provided JSR-303 annotation.
     * We also suport Hibernate @NotEmpty annotation.
     * @param annotation annotation of javax.validation.constraints package.
     * @return "" if annotation is not recognized.
     */
    // FIXME: unit test this
    protected static String translate(Annotation annotation) {
        StringBuilder sb = new StringBuilder();
        // Custom annotations
        if (annotation instanceof Equals) {
            // The annotated element must be equal to target element.
            Equals equalTo = (Equals) annotation;
        // Hibernate validator annotations
        } else if (annotation instanceof NotEmpty) {
            // The annotated element must be not empty.
            sb.append("required: true");
        } else if (annotation instanceof Email) {
            // The annotated element must be a valid email.
            sb.append("email: true");
        // JSR-303 annotations
        } else if (annotation instanceof AssertFalse) {
            // The annotated element must be false.
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof AssertFalse.List) {
            // Defines several @AssertFalse annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof AssertTrue) {
            // The annotated element must be true.
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof AssertTrue.List) {
            // Defines several @AssertTrue annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof DecimalMax) {
            // The annotated element must be a number whose value must be lower or equal to the specified maximum.
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof DecimalMax.List) {
            // Defines several @DecimalMax annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof DecimalMin) {
            // The annotated element must be a number whose value must be higher or equal to the specificed minimum.
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof DecimalMin.List) {
            // Defines several @DecimalMin annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Digits) {
            // The annotated element must be a number within accepted range Supported types are: BigDecimal BigInteger
            // String byte, short, int, long, and their respective wrapper types
            // null elements are considered valid
            Digits digits = (Digits) annotation;
            if (digits.fraction() == 0) {
                sb.append("digits: true, \n");
                sb.append("maxlength: ").append(digits.integer());
            } else {
                // Something like /^\d{1,5}(\.\d{1,2})?$/
                sb.append("pattern: /^\\d{1,").append(digits.integer()).append("}(\\.\\d{1,").append(digits.fraction()).append("})?$/ ");
            }
        } else if (annotation instanceof Digits.List) {
            // Defines several @Digits annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Future) {
            // The annotated element must be a date in the future.
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Future.List) {
            // Defines several @Future annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Max) {
            // The annotated element must be a number whose value must be lower or equal to the specified maximum.
            Max max = (Max) annotation;
            sb.append("number: true, \n");
            sb.append("max: ").append(max.value());
        } else if (annotation instanceof Max.List) {
            // Defines several @Max annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Min) {
            // The annotated element must be a number whose value must be higher or equal to the specified minimum.
            Min min = (Min) annotation;
            sb.append("number: true, \n");
            sb.append("min: ").append(min.value());
        } else if (annotation instanceof Min.List) {
            // Defines several @Min annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof NotNull) {
            // The annotated element must not be null.
            // We interpret it as "required", because NotEmpty does not work with Number.
            sb.append("required: true");
        } else if (annotation instanceof NotNull.List) {
            // Defines several @NotNull annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Null) {
            // The annotated element must be null.
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Null.List) {
            // Defines several @Null annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Past) {
            // The annotated element must be a date in the past.
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Past.List) {
            // Defines several @Past annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Pattern) {
            // The annotated String must match the following regular expression.
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Pattern.List) {
            // Defines several @Pattern annotations on the same element
            throw new UnsupportedOperationException("Not implemented");
        } else if (annotation instanceof Size) {
            // The annotated element size must be between the specified boundaries (included).
            Size size = (Size) annotation;
            if (size.min() > 0) {
                sb.append("required: true, \n");
            }
            sb.append("minlength: ").append(size.min()).append(",\n");
            sb.append("maxlength: ").append(size.max());
        } else if (annotation instanceof Size.List) {
            // Defines several @Size annotations on the same element			
            throw new UnsupportedOperationException("Not implemented");
        }
        return sb.toString();
    }

    /**
     * If an annotation means a required field
     */
    public static boolean isRequred(Annotation annotation) {
        if (annotation instanceof NotEmpty) {
            return true;
        } else if (annotation instanceof NotNull) {
            return true;
        } else if (annotation instanceof Size) {
            Size sizeAnnotation = (Size) annotation;
            return sizeAnnotation.min() > 0;
        }
        return false;
    }
}
