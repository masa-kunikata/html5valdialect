package net.sourceforge.html5val;

import org.thymeleaf.dom.Element;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class FormInputFinder {

    /**
     * Valid values for type attribute of input elements.
     * HTML5 allowed values from http://www.w3.org/TR/html5/the-input-element.html#the-input-element
     */
    private final String[] allowedInputTypes = {
            "text", "search", "tel", "url", "email", "password", "datetime",
            "date", "month", "week", "time", "datetime-local", "number", "range", "color", "checkbox", "radio", "file"
    };

    private List<Element> validInputs = new ArrayList<Element>();

    List<Element> findInputs(Element form) {
        List<Element> inputs = DomUtils.getElementsByTagName(form, "input");
        for (Element input : inputs) {
            addInput(input);
        }
        return validInputs;
    }

    private void addInput(Element input) {
        if (validInputType(input) && notEmptyName(input)) {
            validInputs.add(input);
        }
    }

    private boolean notEmptyName(Element input) {
        String name = getInputName(input);
        return EmptyChecker.notEmpty(name);
    }

    private boolean validInputType(Element element) {
        String type = element.getAttributeValue("type");
        return type != null && ArrayUtils.contains(allowedInputTypes, type);
    }

    /**
     * Returns the input name (i.e. description in <input type="text" name="description" />).
     * In absence of name attribute, takes it from th:field processor. (i.e., th:field="*{description}")
     * Returns null if there are no name or th:field attribute.
     */
    private String getInputName(Element input) {
        if (notEmptyStandardAttribute(input)) {
            return standardAttributeValue(input);
        } else if (notEmptyProcessorAttribute(input)) {
            return processorAttributeValue(input);
        } else {
            return null;
        }
    }

    private boolean notEmptyStandardAttribute(Element input) {
        return standardAttributeValue(input) != null;
    }

    private boolean notEmptyProcessorAttribute(Element input) {
        return input.getAttributeValue("th:field") != null;
    }

    private String standardAttributeValue(Element input) {
        return input.getAttributeValue("name");
    }

    private String processorAttributeValue(Element input) {
        String expression = input.getAttributeValue("th:field");
        if (isProcessorExpression(expression)) {
            return expressionValue(expression);
        } else {
            throw new TemplateProcessingException("Could not retrieve input name from th:field : " + expression);
        }
    }

    private boolean isProcessorExpression(String aux) {
        return aux.indexOf("*{") == 0 && aux.indexOf("}") == aux.length() - 1;
    }

    private String expressionValue(String expression) {
        return expression.substring(2, expression.length() - 1);
    }

}