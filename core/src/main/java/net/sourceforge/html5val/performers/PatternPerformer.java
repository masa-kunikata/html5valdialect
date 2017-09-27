package net.sourceforge.html5val.performers;

import javax.validation.constraints.Pattern;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

import java.util.Arrays;
import java.util.List;

class PatternPerformer implements IValidationPerformer<Pattern> {

    private static final List<String> ALLOWED_TYPE_ATTRS = Arrays.asList(
            "text", "search", "email", "url", "tel", "password"
    );

	@Override
    public Class<Pattern> getConstraintClass() {
        return Pattern.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(Pattern constraint, ITemplateContext context, IProcessableElementTag elementTag) {
        final String type = elementTag.getAttributeValue("type");
        if (ALLOWED_TYPE_ATTRS.contains(type)) {
			final IModelFactory modelFactory = context.getModelFactory();
			IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "pattern", constraint.regexp());
			return modifiedTag;
        }
		return elementTag;
    }
}
