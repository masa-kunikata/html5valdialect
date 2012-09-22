package net.sourceforge.html5val.performers;

import javax.validation.constraints.Min;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

public class MinPerformer implements ValidationPerformer<Min> {

    public Class<Min> getConstraintClass() {
        return Min.class;
    }

    public void putValidationCodeInto(Min constraint, Element element) {
        /**
            // The annotated element must be a number whose value must be higher or equal to the specified minimum.
            Min min = (Min) annotation;
            sb.append("number: true, \n");
            sb.append("min: ").append(min.value());
         */
        throw new UnsupportedOperationException("Not implemented");
    }
}
