package net.sourceforge.html5val.performers;

import org.hibernate.validator.constraints.Email;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

class EmailPerformer implements IValidationPerformer<Email> {

	@Override
    public Class<Email> getConstraintClass() {
        return Email.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(Email constraint, ITemplateContext context, IProcessableElementTag elementTag) {
		final IModelFactory modelFactory = context.getModelFactory();
        IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "type", "email");
		return modifiedTag;
    }
}
