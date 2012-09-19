package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;
import org.thymeleaf.standard.expression.StandardExpressionProcessor;
import org.thymeleaf.util.ArrayUtils;
import static net.sourceforge.html5val.DialectUtil.notEmpty;

/**
 * Client-side validation using jQuery-Validation plugin for a JSR-303 annotated Java class.
 *
 * Could use th:object and th:field attribute values, like:
 * <pre>
 * {@code
 *    <form th:object="${product}" th:validate="${product}">
 *         <input type="text" th:field="*{description}" />
 *    </form>
 * }
 * </pre>
 *
 * Also could use defined values, as in:
 * <pre>
 * {@code
 *    <form th:validate="${product}">
 *         <input type="text" name="description" />
 *    </form>
 * }
 * </pre>
 *
 * A second parameter could be provided to prevent mandatory asterisks HTML generation:
 *
 * <pre>
 * {@code
 *    <form th:validate="${product}, false">
 *         <input type="text" name="description" />
 *    </form>
 * }
 * </pre>
 */
public class ValidateAttrProcessor extends AbstractAttrProcessor {

    /**
     * Valid values for type attribute of input elements.
     * HTML5 allowed values from http://www.w3.org/TR/html5/the-input-element.html#the-input-element
     */
    private final static String[] allowedInputTypes = {"text", "search", "tel", "url", "email", "password",
        "datetime", "date", "month", "week", "time", "datetime-local", "number", "range", "color", "checkbox",
        "radio", "file"};

    /** jQuery-validation-plugin 1.8.1 supported locales. */
    private final static String[] jqueryValidationLanguages = {"ar", "bg", "ca", "cn", "cs", "da", "de", "el",
        "es", "fa", "fi", "fr", "ge", "he", "hu", "it", "ja", "kk", "lt", "lv", "nl", "no", "pl", "ro", "ru",
        "se", "si", "sk", "sr", "th", "tr", "tw", "ua", "vi", "de", "nl", "pt"};

    private Html5ValDialect dialect;

    public ValidateAttrProcessor(String attributeName) {
        super(attributeName);
    }

    public void setDialect(Html5ValDialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public int getPrecedence() {
        return 10000;
    }

    private class Field {
        private String tag;
        private String name;

        public Field(String tag, String name) {
            this.tag = tag;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getTag() {
            return tag;
        }

        public String selector() {
            return this.tag + "[name=\"" + this.name + "\"]";
        }
    }

    /**
     * Returns the input name (i.e. description in <input type="text" name="description" />).
     * In absence of name attribute, takes it from th:field processor. (i.e., th:field="*{description}")
     * Returns null if there are no name or th:field attribute.
     */
    // FIXME: unit test this
    private String getInputName(Element input) {
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

    // FIXME: unit test this
    // FIXME: remove duplicate code
    /**
     * Given a from element, return all valid input names.
     */
    // FIXME: unit-test this
    private List<Field> findFormNames(Element form) {
        List<Field> fields = new ArrayList<Field>();
        List<Element> inputs = DomUtils.getElementsByTagName(form, "input"); // text, checkbox, radio
        for (Element input : inputs) {
            if (input.getAttributeValue("type") != null) {
                String type = input.getAttributeValue("type");
                if (ArrayUtils.contains(allowedInputTypes, type)) {
                    String name = getInputName(input);
                    if (notEmpty(name)) {
                        fields.add(new Field("input", name));
                    }
                }
            }
        }
        List<Element> selects = DomUtils.getElementsByTagName(form, "select"); // select
        for (Element select : selects) {
            if (select.getAttributeValue("name") != null) {
                String name = select.getAttributeValue("name");
                if (notEmpty(name)) {
                    fields.add(new Field("select", name));
                }
            }
        }
        List<Element> textareas = DomUtils.getElementsByTagName(form, "textarea"); // textarea
        for (Element textarea : textareas) {
            if (textarea.getAttributeValue("name") != null) {
                String name = textarea.getAttributeValue("name");
                if (notEmpty(name)) {
                    fields.add(new Field("textarea", name));
                }
            }
        }
        return fields;
    }

    /** Check if a list of fields contains some field. */
    private boolean containsField(java.lang.reflect.Field[] fields, String fieldName) {
        for (java.lang.reflect.Field field :fields) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the @Annotations of a class field.
     * Supports dot-syntax for fieldName, i.e., "store.name" returns the annotations
     * for field "name" of field "store"
     */
    // FIXME: unit-test this
    // FIXME: what if the field name really contais a dot? what happens in spring?
    // FIXME: it ignores fields declared in parent classes
    private Annotation[] getAnnotations(Class clazz, String fieldName) {
        try {
            String currentField = fieldName;
            Class currentClass = clazz;
            while (currentField.indexOf('.') > 0) {
                int dotPos = currentField.indexOf('.');
                String compositeField = currentField.substring(0, dotPos);
                currentField = currentField.substring(dotPos + 1);
                if (!containsField(currentClass.getDeclaredFields(), compositeField)) {
                    return new Annotation[0];
                }
                currentClass = currentClass.getDeclaredField(compositeField).getType();
            }
            if (containsField(currentClass.getDeclaredFields(), currentField)) {
                return currentClass.getDeclaredField(currentField).getAnnotations();
            } else {
                return new Annotation[0];
            }
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    // FIXME: unit-test this
    // FIXME: refactor this (move to other class)
    /**
     * @return null if this field has no validation rules.
     */
    private String buildValidationCodeForField(String fieldName, Annotation[] annotations) {
        boolean someRule = false;
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t\t\"");
        sb.append(fieldName);
        sb.append("\": {\n");
        for (Annotation annotation : annotations) {
            String rule = Jsr303toJQueryBridge.translate(annotation);
            if (notEmpty(rule)) {
                someRule = true;
                sb.append("\t\t\t\t").append(rule).append(",\n");
            }
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append("\t\t\t}");
        if (someRule) {
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * @return if a field is mandatory.
     */
    private boolean isRequiredField(String fieldName, Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (Jsr303toJQueryBridge.isRequred(annotation)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        String attributeValue = element.getAttributeValue(attributeName);
        // Parse arguments
        String[] params = attributeValue.split(",");
        Class formBeanClass = StandardExpressionProcessor.processExpression(arguments, params[0]).getClass();
        boolean generateMandatoryHtml = true;
        if (params.length > 1 && "false".equals(params[1].trim())) {
            generateMandatoryHtml = false;
        }
        // Build javascript code
        boolean someRule = false;
        String formId = DomUtils.getOrCreateId(arguments.getDocument(), element, "form");
        StringBuilder sb = new StringBuilder();
        sb.append("$(\"#").append(formId).append("\").validate({\n");
        StringBuilder rules = new StringBuilder();
        rules.append("\trules: {\n");
        List<Field> fields = findFormNames(element);
        List<Field> requiredFields = new ArrayList<Field>();
        for (Field field : fields) {
            Annotation[] annotations = getAnnotations(formBeanClass, field.getName());
            if (annotations.length > 0) {
                String validationCode = buildValidationCodeForField(field.getName(), annotations);
                if (isRequiredField(field.getName(), annotations)) {
                    requiredFields.add(field);
                }
                if (validationCode != null) {
                    someRule = true;
                    rules.append(validationCode);
                    rules.append(",\n");
                }
            }
        }
        if (someRule) {
            rules.deleteCharAt(rules.length() - 2);
        }
        rules.append("\t}\n");
        if (someRule) {
            sb.append(rules);
        }
        sb.append("});\n");
        StringBuilder selector = new StringBuilder();
        selector.append(".required");
        if (!requiredFields.isEmpty()) {
            for (Field field : requiredFields) {
                selector.append(",#").append(formId).append(" ").append(field.selector());
            }
        }
        if (generateMandatoryHtml) {
            sb.append("$('").append(selector).append("').before('<span class=\"mandatory\">*</span>');\n");
        }
        // Add javascript code to document
        DomUtils.addOnDocumentReady(arguments.getDocument(), sb.toString());
        // Housekeeping
        element.removeAttribute(attributeName);
        return ProcessorResult.OK;
    }
}
