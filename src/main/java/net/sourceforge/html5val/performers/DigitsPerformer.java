package net.sourceforge.html5val.performers;

import javax.validation.constraints.Digits;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

public class DigitsPerformer implements ValidationPerformer<Digits> {

    public Class<Digits> getConstraintClass() {
        return Digits.class;
    }

    public void putValidationCodeInto(Digits digits, Element element) {
        // The annotated element must be a number with certain length in integer and decimal parts.
        String pattern = DigitsRegexpComposer.forDigits(digits).regexp();
        element.setAttribute("pattern", pattern);
    }
}
