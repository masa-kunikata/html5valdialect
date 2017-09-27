package net.sourceforge.html5val.performers;

import org.thymeleaf.dom.Element;

import javax.validation.constraints.Size;
import net.sourceforge.html5val.performers.regexp_composer.LengthRegexpComposer;

public class SizePerformer implements ValidationPerformer<Size> {

    public Class<Size> getConstraintClass() {
        return Size.class;
    }

    public void putValidationCodeInto(Size size, Element element) {
        element.setAttribute("pattern", LengthRegexpComposer.forSize(size).regexp());
        if (size.min() > 0) {
            element.setAttribute("required", "required");
        }
	    if (size.max() > 0 && size.max() < Integer.MAX_VALUE) {
		    element.setAttribute("maxlength", size.max() + "");
	    }
    }
}
