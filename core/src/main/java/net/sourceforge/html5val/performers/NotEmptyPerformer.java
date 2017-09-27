package net.sourceforge.html5val.performers;

import org.hibernate.validator.constraints.NotEmpty;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

class NotEmptyPerformer implements IValidationPerformer<NotEmpty> {

	@Override
    public Class<NotEmpty> getConstraintClass() {
        return NotEmpty.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(NotEmpty constraint, ITemplateContext context, IProcessableElementTag elementTag) {
		final IModelFactory modelFactory = context.getModelFactory();
        IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "required", "required");
		return modifiedTag;
    }
}
