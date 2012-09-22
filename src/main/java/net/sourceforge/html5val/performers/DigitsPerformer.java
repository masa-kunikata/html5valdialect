package net.sourceforge.html5val.performers;

import javax.validation.constraints.Digits;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

public class DigitsPerformer implements ValidationPerformer<Digits> {

    public Class<Digits> getConstraintClass() {
        return Digits.class;
    }

    public void putValidationCodeInto(Digits constraint, Element element) {
        /**
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
         *
         */
        throw new UnsupportedOperationException("Not implemented");
    }
}
