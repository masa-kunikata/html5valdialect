package net.sourceforge.html5val.performers;

import org.hibernate.validator.constraints.NotBlank;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

class NotBlankPerformer implements IValidationPerformer<NotBlank> {

	@Override
    public Class<NotBlank> getConstraintClass() {
        return NotBlank.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(NotBlank constraint, ITemplateContext context, IProcessableElementTag elementTag) {
		final IModelFactory modelFactory = context.getModelFactory();
        IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "required", "required");
		return modifiedTag;
    }
}
