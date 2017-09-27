package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.performers.regexp_composer.LengthRegexpComposer;

import org.hibernate.validator.constraints.Length;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

class LengthPerformer implements IValidationPerformer<Length> {

	@Override
    public Class<Length> getConstraintClass() {
        return Length.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(Length length, ITemplateContext context, IProcessableElementTag elementTag) {
		final IModelFactory modelFactory = context.getModelFactory();
        IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "pattern", LengthRegexpComposer.forLength(length).regexp());
        if (length.min() > 0) {
            modifiedTag = modelFactory.setAttribute(modifiedTag, "required", "required");
        }
	    if (length.max() > 0 && length.max() < Integer.MAX_VALUE) {
		    modifiedTag = modelFactory.setAttribute(modifiedTag, "maxlength", length.max() + "");
	    }
		return modifiedTag;
    }
}
