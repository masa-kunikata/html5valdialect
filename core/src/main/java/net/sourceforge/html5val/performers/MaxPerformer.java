package net.sourceforge.html5val.performers;

import javax.validation.constraints.Max;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

public class MaxPerformer implements ValidationPerformer<Max> {

    public Class<Max> getConstraintClass() {
        return Max.class;
    }

    public void putValidationCodeInto(Max constraint, Element element) {
        // The annotated element must be a number with value lower or equal to the specified maximum.
        Max max = (Max) constraint;
        element.setAttribute("type", "number");
        element.setAttribute("max", Long.toString(max.value()));
    }
}
