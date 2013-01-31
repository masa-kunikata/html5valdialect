package net.sourceforge.html5val;

import org.thymeleaf.dom.Element;
import org.thymeleaf.exceptions.TemplateProcessingException;

import java.util.HashSet;
import java.util.Set;

// FIXME: refactor, remove duplicate code, clean code
public class FormElementFinder {

    /**
     * Given a from element, return all valid input names.
     */
    public static Set<Element> findFormElements(Element form) {
        Set<Element> fields = new HashSet<Element>();
        fields.addAll(FormInputFinder.findInputs(form));
        fields.addAll(FormSelectFinder.findSelects(form));
        fields.addAll(FormTextareaFinder.findTextAreas(form));
        return fields;
    }
}
