package net.sourceforge.html5val.performers;

import javax.validation.constraints.Max;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

public class MaxPerformer implements ValidationPerformer<Max> {

    public Class<Max> getConstraintClass() {
        return Max.class;
    }

    public void putValidationCodeInto(Max constraint, Element element) {
        /**
            // The annotated element must be a number whose value must be lower or equal to the specified maximum.
            Max max = (Max) annotation;
            sb.append("number: true, \n");
            sb.append("max: ").append(max.value());
         */
        throw new UnsupportedOperationException("Not implemented");
    }
}
