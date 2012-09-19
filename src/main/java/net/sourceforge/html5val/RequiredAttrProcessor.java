package net.sourceforge.html5val;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

/**
 * This is a workaround for validating LocalizedString required fields.
 * Validation on the server side is performed well using @NotEmptyLocalizedString, but
 * it does not work on the client side.
 * This attribute only adds class="required" to a input to force jQuery-validation.
 * We use this attribute to remark the bug, because of the class="required" weak semantics.
 * Usage:
 *
 * <pre>
 * {@code
 *    <input type="text" jquery:required="required" />
 * }
 * </pre>
 */
public class RequiredAttrProcessor extends AbstractAttrProcessor {

    private Html5ValDialect dialect;

    public RequiredAttrProcessor(String attributeName) {
        super(attributeName);
    }

    public void setDialect(Html5ValDialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public int getPrecedence() {
        return 100;
    }

    @Override
    public ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        String attributeValue = element.getAttributeValue(attributeName);
        if ("required".equals(attributeValue)) {
            DomUtils.addAttributeValue(element, "class", "required");
        }
        // Housekeeping
        element.removeAttribute(attributeName);
        return ProcessorResult.OK;
    }
}
