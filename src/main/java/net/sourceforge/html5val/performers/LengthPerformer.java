package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import org.hibernate.validator.constraints.Length;
import org.thymeleaf.dom.Element;

public class LengthPerformer implements ValidationPerformer<Length> {

    public Class<Length> getConstraintClass() {
        return Length.class;
    }

    public void putValidationCodeInto(Length length, Element element) {
        int min = length.min();
        int max = length.max();
        element.setAttribute("pattern", LengthRegexpComposer.forMinAndMax(min, max).regexp());
    }
}
