package net.sourceforge.html5val;

import org.thymeleaf.dom.Element;

import java.util.HashSet;
import java.util.Set;

public class FormElementFinder {

    /**
     * Given a from element, return all valid input names.
     */
    public static Set<Element> findFormElements(Element form) {
        Set<Element> fields = new HashSet<Element>();
        fields.addAll(new FormInputFinder().findInputs(form));
        fields.addAll(new FormSelectFinder().findSelects(form));
        fields.addAll(new FormTextareaFinder().findTextAreas(form));
        return fields;
    }
}
