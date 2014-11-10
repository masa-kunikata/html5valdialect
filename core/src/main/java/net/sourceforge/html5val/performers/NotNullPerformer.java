package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

import javax.validation.constraints.NotNull;

public class NotNullPerformer implements ValidationPerformer<NotNull> {

    public Class<NotNull> getConstraintClass() {
        return NotNull.class;
    }

    public void putValidationCodeInto(NotNull constraint, Element element) {
        element.setAttribute("required", "required");
    }
}
