package net.sourceforge.html5val;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

public class ValidatePreviousFormAttrProcessor extends AbstractAttrProcessor {

    public static final String ATTR_NAME = "validatePreviousForm";

    public ValidatePreviousFormAttrProcessor() {
        super(ATTR_NAME);
    }

    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    public ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        new ValidatePreviousFormCommand(arguments, element, attributeName).execute();
        return ProcessorResult.OK;
    }
}
