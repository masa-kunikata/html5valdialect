package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import org.hibernate.validator.constraints.Email;
import org.thymeleaf.dom.Element;

public class EmailPerformer implements ValidationPerformer<Email> {

    public Class<Email> getConstraintClass() {
        return Email.class;
    }

    public void putValidationCodeInto(Email constraint, Element element) {
        element.setAttribute("type", "email");
    }
}
