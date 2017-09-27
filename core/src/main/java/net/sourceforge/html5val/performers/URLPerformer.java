package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.performers.regexp_composer.URLRegexpComposer;

import org.hibernate.validator.constraints.URL;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModelFactory;

class URLPerformer implements IValidationPerformer<URL> {

	@Override
    public Class<URL> getConstraintClass() {
        return URL.class;
    }

	@Override
    public IProcessableElementTag toValidationTag(URL constraint, ITemplateContext context, IProcessableElementTag elementTag) {
        URL url = (URL) constraint;
		final IModelFactory modelFactory = context.getModelFactory();
        IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "type", "url");
        modifiedTag = modelFactory.setAttribute(modifiedTag, "pattern", URLRegexpComposer.forURL(url).regexp());
		return modifiedTag;
    }
}
