package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.performers.regexp_composer.LengthRegexpComposer;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

import javax.validation.constraints.Size;

class SizePerformer implements IValidationPerformer<Size> {

	@Override
    public Class<Size> getConstraintClass() {
        return Size.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(Size size, ITemplateContext context, IProcessableElementTag elementTag) {
		final IModelFactory modelFactory = context.getModelFactory();
        IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "pattern", LengthRegexpComposer.forSize(size).regexp());
        if (size.min() > 0) {
			modifiedTag = modelFactory.setAttribute(modifiedTag, "required", "required");
        }
	    if (size.max() > 0 && size.max() < Integer.MAX_VALUE) {
		    modifiedTag = modelFactory.setAttribute(modifiedTag, "maxlength", size.max() + "");
	    }
		return modifiedTag;
    }
}
