package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

import javax.validation.constraints.Size;

public class SizePerformer implements ValidationPerformer<Size> {

    public Class<Size> getConstraintClass() {
        return Size.class;
    }

    public void putValidationCodeInto(Size size, Element element) {
        element.setAttribute("pattern", LengthRegexpComposer.forSize(size).regexp());
        if (size.min() > 0) {
            element.setAttribute("required", "required");
        }
    }
}
