package net.sourceforge.html5val.performers;

import org.hibernate.validator.constraints.URL;
import org.thymeleaf.dom.Element;
import net.sourceforge.html5val.performers.regexp_composer.URLRegexpComposer;

public class URLPerformer implements ValidationPerformer<URL> {

    public Class<URL> getConstraintClass() {
        return URL.class;
    }

    public void putValidationCodeInto(URL constraint, Element element) {
        URL url = (URL) constraint;
        element.setAttribute("type", "url");
        element.setAttribute("pattern", URLRegexpComposer.forURL(url).regexp());
    }
}
