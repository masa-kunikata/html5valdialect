package net.sourceforge.html5val.performers;

import javax.validation.constraints.Pattern;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

public class PatternPerformer implements ValidationPerformer<Pattern> {

    public Class<Pattern> getConstraintClass() {
        return Pattern.class;
    }

    public void putValidationCodeInto(Element element) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
