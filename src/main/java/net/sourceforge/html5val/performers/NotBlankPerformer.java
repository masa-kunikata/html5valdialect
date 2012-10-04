package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import org.hibernate.validator.constraints.NotBlank;
import org.thymeleaf.dom.Element;

public class NotBlankPerformer implements ValidationPerformer<NotBlank> {

    public Class<NotBlank> getConstraintClass() {
        return NotBlank.class;
    }

    public void putValidationCodeInto(NotBlank constraint, Element element) {
        element.setAttribute("required", "required");
    }
}
