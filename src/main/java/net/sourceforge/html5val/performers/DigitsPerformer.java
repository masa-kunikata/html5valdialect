package net.sourceforge.html5val.performers;

import javax.validation.constraints.Digits;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

public class DigitsPerformer implements ValidationPerformer<Digits> {
    
    private StringBuilder sb;

    public Class<Digits> getConstraintClass() {
        return Digits.class;
    }

    public void putValidationCodeInto(Digits digits, Element element) {
        // The annotated element must be a number within accepted range
        // Pattern example: [0-9]{1,4}(\\.[0-9]{1,2})?
        sb = new StringBuilder();
        putIntegerPartRegex(digits);
        putFractionalPartRegex(digits);
        putAttributeInElement(element);
    }

    private void putIntegerPartRegex(Digits digits) {
        if (digits.integer() > 0) {
            sb.append("[0-9]{1,").append(digits.integer()).append("}");
        } else {
            sb.append("[0-9]?");
        }
    }

    private void putFractionalPartRegex(Digits digits) {
        if (digits.fraction() > 0) {
            if(digits.integer() == 0) {
                sb.append("\\.[0-9]{1,").append(digits.fraction()).append("}");
            } else {
                sb.append("(\\.[0-9]{1,").append(digits.fraction()).append("})?");
            }
        }
    }

    private void putAttributeInElement(Element element) {
        if (sb.length() > 0) {
            element.setAttribute("pattern", sb.toString());
        }
    }
}
