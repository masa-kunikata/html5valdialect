package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import org.hibernate.validator.constraints.Length;
import org.thymeleaf.dom.Element;

public class LengthPerformer implements ValidationPerformer<Length> {

    public Class<Length> getConstraintClass() {
        return Length.class;
    }

    public void putValidationCodeInto(Length length, Element element) {
        element.setAttribute("pattern", LengthRegexpComposer.forLength(length).regexp());
        if (length.min() > 0) {
            element.setAttribute("required", "required");
        }
    }
}
