package net.sourceforge.html5val.performers;

import javax.validation.constraints.Size;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

public class SizePerformer implements ValidationPerformer<Size> {

    public Class<Size> getConstraintClass() {
        return Size.class;
    }

    public void putValidationCodeInto(Size size, Element element) {
        int min = size.min();
        int max = size.max();
        element.setAttribute("pattern", LengthRegexpComposer.forMinAndMax(min, max).regexp());
    }
}
