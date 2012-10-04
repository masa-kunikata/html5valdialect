package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import org.hibernate.validator.constraints.Range;
import org.thymeleaf.dom.Element;

public class RangePerformer implements ValidationPerformer<Range> {

    public Class<Range> getConstraintClass() {
        return Range.class;
    }

    public void putValidationCodeInto(Range constraint, Element element) {
        // The annotated element has to be in the appropriate range. 
        Range range = (Range) constraint;
        element.setAttribute("type", "range");
        element.setAttribute("min", Long.toString(range.min()));
        element.setAttribute("max", Long.toString(range.max()));
    }
}
