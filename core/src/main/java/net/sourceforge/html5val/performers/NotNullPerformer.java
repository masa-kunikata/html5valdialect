package net.sourceforge.html5val.performers;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

import javax.validation.constraints.NotNull;

class NotNullPerformer implements IValidationPerformer<NotNull> {

	@Override
    public Class<NotNull> getConstraintClass() {
        return NotNull.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(NotNull constraint, ITemplateContext context, IProcessableElementTag elementTag) {
		final IModelFactory modelFactory = context.getModelFactory();
        IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "required", "required");
		return modifiedTag;
    }
}
