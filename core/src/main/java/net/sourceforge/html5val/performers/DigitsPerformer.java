package net.sourceforge.html5val.performers;

import javax.validation.constraints.Digits;
import net.sourceforge.html5val.performers.regexp_composer.DigitsRegexpComposer;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

class DigitsPerformer implements IValidationPerformer<Digits> {

	@Override
    public Class<Digits> getConstraintClass() {
        return Digits.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(Digits digits, ITemplateContext context, IProcessableElementTag elementTag) {
        // The annotated element must be a number with certain length in integer and decimal parts.
        String pattern = DigitsRegexpComposer.forDigits(digits).regexp();
		final IModelFactory modelFactory = context.getModelFactory();
        IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "pattern", pattern);
		return modifiedTag;
    }
}
