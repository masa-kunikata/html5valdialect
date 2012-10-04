package net.sourceforge.html5val.performers;

import javax.validation.constraints.Digits;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

public class DigitsPerformer implements ValidationPerformer<Digits> {

    public Class<Digits> getConstraintClass() {
        return Digits.class;
    }

    public void putValidationCodeInto(Digits digits, Element element) {
        // The annotated element must be a number within accepted range
        // Pattern example: [0-9]{1,4}(\\.[0-9]{1,2})?
        StringBuilder sb = new StringBuilder();
        if (digits.integer() > 0) {
            sb.append("[0-9]{1,").append(digits.integer()).append("}");
        }
        if (digits.fraction() > 0) {
            sb.append("(\\.[0-9]{1,").append(digits.fraction()).append("})?");
        }
        if (sb.length() > 0) {
            element.setAttribute("pattern", sb.toString());
        }
    }
}
