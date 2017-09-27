package net.sourceforge.html5val.performers;

import javax.validation.constraints.Min;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

class MinPerformer implements IValidationPerformer<Min> {

	@Override
    public Class<Min> getConstraintClass() {
        return Min.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(Min constraint, ITemplateContext context, IProcessableElementTag elementTag) {
        // The annotated element must be a number with value greater or equal to the specified minimum.
        Min min = (Min) constraint;
		final IModelFactory modelFactory = context.getModelFactory();
        IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "type", "number");
        modifiedTag = modelFactory.setAttribute(modifiedTag, "min", Long.toString(min.value()));
		return modifiedTag;
    }
}
