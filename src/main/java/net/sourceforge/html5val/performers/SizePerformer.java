package net.sourceforge.html5val.performers;

import javax.validation.constraints.Size;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

public class SizePerformer implements ValidationPerformer<Size> {

    public Class<Size> getConstraintClass() {
        return Size.class;
    }

    public void putValidationCodeInto(Size size, Element element) {
        if (size.min() > 0) {
            element.setAttribute("required", "required");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(".{");
        sb.append(size.min()).append(",");
        if (size.max() < Integer.MAX_VALUE) {
            sb.append(size.max());
        }
        sb.append("}");
        element.setAttribute("pattern", sb.toString());
    }
}
