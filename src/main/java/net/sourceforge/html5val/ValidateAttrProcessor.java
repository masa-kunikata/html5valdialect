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

    private final ValidationPerformerFactory validationPerformerFactory = new ValidationPerformerFactory();

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

    private void processFieldValidation(Element field, Class jsr303AnnotatedClass) {
        Annotation[] constraints = AnnotationExtractor.forClass(jsr303AnnotatedClass).getAnnotationsFor(field);
        for (Annotation constraint : constraints) {
            ValidationPerformer processor = validationPerformerFactory.getProcessorFor(constraint);
            processor.putValidationCodeInto(field);
        }
    }
}
