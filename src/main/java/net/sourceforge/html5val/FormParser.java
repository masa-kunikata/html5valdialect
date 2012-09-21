package net.sourceforge.html5val;

import java.util.ArrayList;
import java.util.List;
import org.thymeleaf.dom.Element;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.util.ArrayUtils;
import static net.sourceforge.html5val.EmptyChecker.notEmpty;

public class FormParser {
    /**
     * Valid values for type attribute of input elements.
     * HTML5 allowed values from http://www.w3.org/TR/html5/the-input-element.html#the-input-element
     */
    private static final String[] allowedInputTypes = {"text", "search", "tel", "url", "email", "password", "datetime", "date", "month", "week", "time", "datetime-local", "number", "range", "color", "checkbox", "radio", "file"};


    /**
     * Given a from element, return all valid input names.
     */
    // FIXME: remove duplicate code
    public static List<Element> findFormElements(Element form) {
        List<Element> fields = new ArrayList<Element>();
        List<Element> inputs = DomUtils.getElementsByTagName(form, "input"); // text, checkbox, radio
        // text, checkbox, radio
        for (Element input : inputs) {
            if (input.getAttributeValue("type") != null) {
                String type = input.getAttributeValue("type");
                if (ArrayUtils.contains(allowedInputTypes, type)) {
                    String name = getInputName(input);
                    if (notEmpty(name)) {
                        fields.add(input);
                    }
                }
            }
        }
        List<Element> selects = DomUtils.getElementsByTagName(form, "select"); // select
        // select
        for (Element select : selects) {
            if (select.getAttributeValue("name") != null) {
                String name = select.getAttributeValue("name");
                if (notEmpty(name)) {
                    fields.add(select);
                }
            }
        }
        List<Element> textareas = DomUtils.getElementsByTagName(form, "textarea"); // textarea
        // textarea
        for (Element textarea : textareas) {
            if (textarea.getAttributeValue("name") != null) {
                String name = textarea.getAttributeValue("name");
                if (notEmpty(name)) {
                    fields.add(textarea);
                }
            }
        }
        return fields;
    }

    /**
     * Returns the input name (i.e. description in <input type="text" name="description" />).
     * In absence of name attribute, takes it from th:field processor. (i.e., th:field="*{description}")
     * Returns null if there are no name or th:field attribute.
     */
    private static String getInputName(Element input) {
        if (input.getAttributeValue("name") != null) {
            return input.getAttributeValue("name");
        } else if (input.getAttributeValue("th:field") != null) {
            String aux = input.getAttributeValue("th:field");
            if (aux.indexOf("*{") == 0 && aux.indexOf("}") == aux.length() - 1) {
                return aux.substring(2, aux.length() - 1);
            } else {
                throw new TemplateProcessingException("Could not retrieve input name from th:field : " + aux);
            }
        } else {
            return null;
        }
    }
}
