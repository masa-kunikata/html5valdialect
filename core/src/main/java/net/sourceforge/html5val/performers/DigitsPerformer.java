package net.sourceforge.html5val.performers;

import javax.validation.constraints.Digits;
import org.thymeleaf.dom.Element;
import net.sourceforge.html5val.performers.regexp_composer.DigitsRegexpComposer;

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
