package com.msd_sk.thymeleaf.dialects.html5val;

import java.util.Arrays;
import java.util.List;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Client-side validation using HTML5 validation for a JSR-303 annotated Java class.
 * <p/>
 * Usage:
 * <pre>
 * {@code
 *    <form val:validate="${modelInstance}">
 *         <input type="text" th:field="*{property}" />
 *    </form>
 * }
 * </pre>
 */
public class ValidateAttributeModelProcessor extends AbstractAttributeModelProcessor {

    private static final String ATTR_NAME = "validate";
    private static final int PRECEDENCE = 10000;

    public ValidateAttributeModelProcessor(final String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix, // Prefix to be applied to name for matching
                null, // tag name: not specified
                false, // No prefix to be applied to tag name
                ATTR_NAME, // Name of the attribute that will be matched
                true, // Apply dialect prefix to attribute name
                PRECEDENCE, // Precedence (inside dialect's own precedence)
                true); // Remove the matched attribute afterwards
    }

    private static final List<String> TAG_NAMES = Arrays.asList("form", "th:block");

    protected void doProcess(
            final ITemplateContext context, final IModel model,
            final AttributeName attributeName, final String attributeValue,
            final IElementModelStructureHandler structureHandler) {

        final List<IProcessableElementTag> elementStack = context.getElementStack();
        final IProcessableElementTag currentElement = elementStack.get(elementStack.size() - 1);

        if (!TAG_NAMES.contains(currentElement.getElementCompleteName())) {
            return;
        }
        ;

        ValidateAttributeCommands.DEFAULT.execute(context, model, attributeValue);
    }

}
