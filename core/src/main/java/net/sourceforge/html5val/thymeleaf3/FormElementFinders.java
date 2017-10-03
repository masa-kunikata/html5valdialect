package net.sourceforge.html5val.thymeleaf3;

import java.util.LinkedHashMap;
import java.util.Map;

import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.util.ArrayUtils;

import net.sourceforge.html5val.util.EmptyChecker;

public enum FormElementFinders {
    /**  */
    INPUT_FINDER() {
        protected Map<Integer, IProcessableElementTag> find(final IModel form) {
            Map<Integer, IProcessableElementTag> ret = new LinkedHashMap<>();
            Map<Integer, IProcessableElementTag> tags = ModelUtils.getElementsByTagName(form, "input");
            for (Map.Entry<Integer, IProcessableElementTag> tag : tags.entrySet()) {
                if (validInputType(tag.getValue()) && notEmptyName(tag.getValue())) {
                    ret.put(tag.getKey(), tag.getValue());
                }
            }
            return ret;
        }

        /*
         * Valid values for type attribute of input elements.
         * HTML5 allowed values from http://www.w3.org/TR/html5/the-input-element.html#the-input-element
         */
        private final String[] allowedInputTypes = {
                "text", "search", "tel", "url", "email", "password", "datetime",
                "date", "month", "week", "time", "datetime-local", "number", "range", "color", "checkbox", "radio",
                "file"
        };

        private boolean validInputType(IProcessableElementTag element) {
            String type = element.getAttributeValue("type");
            return type != null && ArrayUtils.contains(allowedInputTypes, type);
        }

        private boolean notEmptyName(IProcessableElementTag input) {
            String name = getInputName(input);
            return EmptyChecker.notEmpty(name);
        }

        /*
         * Returns the input name (i.e. description in <input type="text" name="description" />).
         * In absence of name attribute, takes it from th:field processor. (i.e., th:field="*{description}")
         * Returns null if there are no name or th:field attribute.
         */
        private String getInputName(IProcessableElementTag input) {
            if (notEmptyStandardAttribute(input)) {
                return standardAttributeValue(input);
            } else if (notEmptyProcessorAttribute(input)) {
                return processorAttributeValue(input);
            } else {
                return null;
            }
        }

        private boolean notEmptyStandardAttribute(IProcessableElementTag input) {
            return standardAttributeValue(input) != null;
        }

        private boolean notEmptyProcessorAttribute(IProcessableElementTag input) {
            return input.getAttributeValue("th:field") != null;
        }

        private String standardAttributeValue(IProcessableElementTag input) {
            return input.getAttributeValue("name");
        }

        private String processorAttributeValue(IProcessableElementTag input) {
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
    },
    /**  */
    SELECT_FINDER() {
        protected Map<Integer, IProcessableElementTag> find(final IModel form) {
            Map<Integer, IProcessableElementTag> ret = new LinkedHashMap<>();
            Map<Integer, IProcessableElementTag> tags = ModelUtils.getElementsByTagName(form, "select");
            for (Map.Entry<Integer, IProcessableElementTag> tag : tags.entrySet()) {
                if (hasNotEmptyName(tag.getValue())) {
                    ret.put(tag.getKey(), tag.getValue());
                }
            }
            return ret;
        }

        private boolean hasNotEmptyName(IProcessableElementTag tag) {
            String name = tag.getAttributeValue("name");
            return EmptyChecker.notEmpty(name);
        }
    },
    /**  */
    TEXTAREA_FINDER() {
        protected Map<Integer, IProcessableElementTag> find(final IModel form) {
            Map<Integer, IProcessableElementTag> ret = new LinkedHashMap<>();
            Map<Integer, IProcessableElementTag> tags = ModelUtils.getElementsByTagName(form, "textarea");
            for (Map.Entry<Integer, IProcessableElementTag> tag : tags.entrySet()) {
                if (hasNotEmptyName(tag.getValue())) {
                    ret.put(tag.getKey(), tag.getValue());
                }
            }
            return ret;
        }

        private boolean hasNotEmptyName(IProcessableElementTag tag) {
            String name = tag.getAttributeValue("name");
            return EmptyChecker.notEmpty(name);
        }
    },
    ;

    protected abstract Map<Integer, IProcessableElementTag> find(final IModel form);

    /**
     * Given a from element, return all valid input names.
     */
    public static Map<Integer, IProcessableElementTag> findFormElements(final IModel form) {
        Map<Integer, IProcessableElementTag> tags = new LinkedHashMap<>();
        for (FormElementFinders finder : FormElementFinders.values()) {
            Map<Integer, IProcessableElementTag> findResult = finder.find(form);
            if (null != findResult)
                tags.putAll(findResult);
        }
        return tags;
    }
}
