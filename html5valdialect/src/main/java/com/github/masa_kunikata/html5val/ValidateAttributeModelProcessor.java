package com.github.masa_kunikata.html5val;

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
 * Client-side validation using HTML5 validation for a JSR-380 annotated Java class.
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
    //I do not know how to communicate this problem. So I am using code comment.
    //There is a problem with Spring CSRF token protection and this library.
    //CSRF token insertion is handled with one of the thymeleaf processors (elementTagProcessor with precedence 1000), which inserts csrf token into the form.
    //Then in ProcessorTemplateHandler::handleOpenElement vars.modelAfter (or vars.modelBefore, I am not sure now) is not empty,
    //after processing this thymeleaf elementTagProcessor processor.
    //And then jumps this ValidateAttributeModelProcessor into the game. It fails on line 1479 (ProcessorTemplateHandler),
    //because vars.modelAfter is not empty. What results into TemplateProcessingException.
    //To avoid this problem we need run this processor before Thymeleaf processor which inserts CSRF token.
    //For now it works for us in our project without any side efffects.
    //But I am still not sure, if this is the best solution.
    private static final int PRECEDENCE = 999;

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
