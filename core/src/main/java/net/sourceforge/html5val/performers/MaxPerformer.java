package net.sourceforge.html5val.performers;

import javax.validation.constraints.Max;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

class MaxPerformer implements IValidationPerformer<Max> {

	@Override
    public Class<Max> getConstraintClass() {
        return Max.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(Max constraint, ITemplateContext context, IProcessableElementTag elementTag) {
        // The annotated element must be a number with value lower or equal to the specified maximum.
        Max max = (Max) constraint;
		final IModelFactory modelFactory = context.getModelFactory();
        IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "type", "number");
        modifiedTag = modelFactory.setAttribute(modifiedTag, "max", Long.toString(max.value()));
		return modifiedTag;
    }
}
