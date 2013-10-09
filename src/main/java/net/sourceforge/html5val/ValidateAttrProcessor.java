package net.sourceforge.html5val;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

/**
 * Client-side validation using HTML5 validation for a JSR-303 annotated Java class.
 * <p/>
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

    public static final String ATTR_NAME = "validate";

    public ValidateAttrProcessor() {
        super(ATTR_NAME);
    }

    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    public ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        new ValidateCommand(element, attributeName).execute();
        return ProcessorResult.OK;
    }
}
