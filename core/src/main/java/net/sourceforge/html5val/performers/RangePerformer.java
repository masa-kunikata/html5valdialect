package net.sourceforge.html5val.performers;

import org.hibernate.validator.constraints.Range;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

class RangePerformer implements IValidationPerformer<Range> {

	@Override
    public Class<Range> getConstraintClass() {
        return Range.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(Range constraint, ITemplateContext context, IProcessableElementTag elementTag) {
        // The annotated element has to be in the appropriate range. 
        Range range = (Range) constraint;
		final IModelFactory modelFactory = context.getModelFactory();
        IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "type", "range");
        modifiedTag = modelFactory.setAttribute(modifiedTag, "min", Long.toString(range.min()));
        modifiedTag = modelFactory.setAttribute(modifiedTag, "max", Long.toString(range.max()));
		return modifiedTag;
    }
}
