package net.sourceforge.html5val;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.processor.element.AbstractAttributeModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 */
public class ValidateFormAttributeModelDelayedProcessor extends AbstractAttributeModelProcessor {

    static final String TAG_NAME = "div";
    static final String ATTR_NAME = "validateOuterForm";
    private static final int PRECEDENCE = 10010;

    public ValidateFormAttributeModelDelayedProcessor(final String dialectPrefix) {
        super(
            TemplateMode.HTML, // This processor will apply only to HTML mode
            dialectPrefix,     // Prefix to be applied to name for matching
            TAG_NAME,          // tag name: match the tag
            false,             // No prefix to be applied to tag name
            ATTR_NAME,         // Name of the attribute that will be matched
            true,              // Apply dialect prefix to attribute name
            PRECEDENCE,        // Precedence (inside dialect's own precedence)
            true);             // Remove the matched attribute afterwards
    }

    protected void doProcess(
            final ITemplateContext context, final IModel model,
            final AttributeName attributeName, final String attributeValue,
            final IElementModelStructureHandler structureHandler) {
		ValidateFormCommands.OUTER_FORM_COMMAND.execute(context, model, attributeValue);
    }


}
