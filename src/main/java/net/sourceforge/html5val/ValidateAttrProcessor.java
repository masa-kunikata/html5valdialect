package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.util.Set;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;
import org.thymeleaf.standard.expression.StandardExpressionProcessor;
import static net.sourceforge.html5val.FormElementFinder.findFormElements;

/**
 * Client-side validation using HTML5 validation for a JSR-303 annotated Java class.
 *
 * Usage:
 * <pre>
 * {@code
 *    <form th:validate="${product}">
 *         <input type="text" name="description" />
 *    </form>
 * }
 * </pre>
 */
public class ValidateAttrProcessor extends AbstractAttrProcessor {

    public ValidateAttrProcessor(String attributeName) {
        super(attributeName);
    }

    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    public ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        Class jsr303AnnotatedClass = jsr303AnnotatedClass(arguments, element, attributeName);
        Set<Element> fields = findFormElements(element);
        for (Element field : fields) {
            processFieldValidation(field, jsr303AnnotatedClass);
        }
        // Housekeeping
        element.removeAttribute(attributeName);
        return ProcessorResult.OK;
    }

    private Class jsr303AnnotatedClass(Arguments arguments, Element element, String attributeName) {
        String attributeValue = element.getAttributeValue(attributeName);
        return StandardExpressionProcessor.processExpression(arguments, attributeValue).getClass();
    }

    private void processFieldValidation(Element fieldElement, Class jsr303AnnotatedClass) {
        String fieldName = getFieldName(fieldElement);
        if (fieldName != null) {
            Annotation[] constraints = AnnotationExtractor.forClass(jsr303AnnotatedClass).getAnnotationsFor(fieldName);
            for (Annotation constraint : constraints) {
                ValidationPerformer processor = ValidationPerformerFactory.getPerformerFor(constraint);
                processor.putValidationCodeInto(constraint, fieldElement);
            }
        }
    }

    private String getFieldName(Element fieldElement) {
        String TH_FIELD = "th:field"; // FIXME: get dynamic prefix
        if (fieldElement.getAttributeValue("name") != null) {
            return fieldElement.getAttributeValue("name");
        } else if (fieldElement.getAttributeValue(TH_FIELD) != null && fieldElement.getAttributeValue(TH_FIELD).startsWith("*")) {
            String value = fieldElement.getAttributeValue(TH_FIELD);
            return value.substring(2, value.length() - 1);
        }
        return null;
    }
}
