package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import org.hibernate.validator.constraints.NotEmpty;
import org.thymeleaf.dom.Element;

public class NotEmptyPerformer implements ValidationPerformer<NotEmpty> {

    public Class<NotEmpty> getConstraintClass() {
        return NotEmpty.class;
    }

    public void putValidationCodeInto(NotEmpty constraint, Element element) {
        element.setAttribute("required", "required");
    }
}
