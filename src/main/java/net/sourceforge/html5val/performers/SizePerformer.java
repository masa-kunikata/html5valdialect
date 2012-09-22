package net.sourceforge.html5val.performers;

import javax.validation.constraints.Size;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

public class SizePerformer implements ValidationPerformer<Size> {

    public Class<Size> getConstraintClass() {
        return Size.class;
    }

    public void putValidationCodeInto(Element element) {
        /**
            // The annotated element size must be between the specified boundaries (included).
            Size size = (Size) annotation;
            if (size.min() > 0) {
                sb.append("required: true, \n");
            }
            sb.append("minlength: ").append(size.min()).append(",\n");
            sb.append("maxlength: ").append(size.max());
         */
        throw new UnsupportedOperationException("Not implemented");
    }
}
