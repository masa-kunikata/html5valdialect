package net.sourceforge.html5val.performers;

import javax.validation.constraints.Pattern;
import net.sourceforge.html5val.ValidationPerformer;
import org.thymeleaf.dom.Element;

import java.util.Arrays;
import java.util.List;

public class PatternPerformer implements ValidationPerformer<Pattern> {

    private static final List<String> ALLOWED_TYPE_ATTRS = Arrays.asList(
            "text", "search", "email", "url", "tel", "password"
    );

    public Class<Pattern> getConstraintClass() {
        return Pattern.class;
    }

    public void putValidationCodeInto(Pattern constraint, Element element) {
        String type = element.getAttributeValue("type");
        if (ALLOWED_TYPE_ATTRS.contains(type)) {
            element.setAttribute("pattern", constraint.regexp());
        }
    }
}
