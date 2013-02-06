package net.sourceforge.html5val;

import org.thymeleaf.dom.Element;

import static net.sourceforge.html5val.DelayedProcessorInvoker.invokeProcessorAfterElement;

/**
 * As Thymeleaf runs a processor before the evaluation of its children, th:include contents are not
 * aware at this stage. To process all contents, we create a delayed invoker to perform form modification
 * after the evaluation of form contents.
 */
public class ValidateCommand {

    private Element element;

    private String attributeName;

    public ValidateCommand(Element element, String attributeName) {
        this.element = element;
        this.attributeName = attributeName;
    }

    public void execute() {
        String processorAttributeName = getDialectPrefix() + ":" + ValidatePreviousFormAttrProcessor.ATTR_NAME; // val:validatePreviousForm
        String processorAttributeValue = element.getAttributeValue(attributeName);
        invokeProcessorAfterElement(element, processorAttributeName, processorAttributeValue);
        element.removeAttribute(attributeName);
    }

    private String getDialectPrefix() {
        // FIXME: ATTR_PREFIX could be changed by configuration
        return Html5ValDialect.ATTR_PREFIX;
    }
}