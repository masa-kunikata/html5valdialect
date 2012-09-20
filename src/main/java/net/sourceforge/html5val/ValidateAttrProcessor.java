package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.util.List;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;
import org.thymeleaf.standard.expression.StandardExpressionProcessor;

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
        Class formValidationClass = formValidationClass(arguments, element, attributeName);
        List<Element> fields = new FormParser().findFormElements(element);
        for (Element field : fields) {
            String fieldName = field.getAttributeValue("name");
            Annotation[] annotations = new AnnotationUtil().annotationsFor(formValidationClass, fieldName);
            if (annotations.length > 0) {
                validationForField(fieldName, annotations);
            }
        }
        // Housekeeping
        element.removeAttribute(attributeName);
        return ProcessorResult.OK;
    }

    private Class formValidationClass(Arguments arguments, Element element, String attributeName) {
        String attributeValue = element.getAttributeValue(attributeName);
        return StandardExpressionProcessor.processExpression(arguments, attributeValue).getClass();
    }

    /**
     * @return null if this field has no validation rules.
     */
    private void validationForField(String fieldName, Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            Jsr303toJQueryBridge.translate(annotation);
        }
    }
}
