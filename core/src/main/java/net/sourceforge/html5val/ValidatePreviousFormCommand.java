package net.sourceforge.html5val;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static net.sourceforge.html5val.thymeleaf3.FormElementFinder.findFormElements;
import static net.sourceforge.html5val.thymeleaf3.DomUtils.findPreviousElement;
import static net.sourceforge.html5val.thymeleaf3.DomUtils.removeElement;
import net.sourceforge.html5val.thymeleaf3.ExpressionUtil;

import net.sourceforge.html5val.reflect.AnnotationExtractor;
import net.sourceforge.html5val.performers.ValidationPerformer;
import net.sourceforge.html5val.performers.ValidationPerformerFactory;

public class ValidatePreviousFormCommand {

    private Arguments arguments;

    private Element element;

    private String attributeName;

    private Class jsr303AnnotatedClass;

    private Element formElement;

    public ValidatePreviousFormCommand(Arguments arguments, Element element, String attributeName) {
        this.arguments = arguments;
        this.element = element;
        this.attributeName = attributeName;
    }

    public void execute() {
        readJsr303AnnotatedClass();
        findPreviousFormElement();
        processFields();
        removeElement(element);
    }

    private void readJsr303AnnotatedClass() {
        String attributeValue = element.getAttributeValue(attributeName);
        jsr303AnnotatedClass = ExpressionUtil.evaluate(arguments, attributeValue).getClass();
    }

    private void findPreviousFormElement() {
        formElement = findPreviousElement(element, "form");
    }

    private void processFields() {
        Set<Element> fields = findFormElements(formElement);
        for (Element field : fields) {
            processFieldValidation(field);
        }
    }

    private void processFieldValidation(Element fieldElement) {
        String fieldName = getFieldName(fieldElement);
        List<Annotation> constraints = AnnotationExtractor.forClass(jsr303AnnotatedClass).getAnnotationsForField(fieldName);
        for (Annotation constraint : constraints) {
            ValidationPerformer processor = ValidationPerformerFactory.getPerformerFor(constraint);
            processor.putValidationCodeInto(constraint, fieldElement);
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
