package net.sourceforge.html5val.performers;

import java.lang.annotation.Annotation;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;

public interface IValidationPerformer {

    Class<? extends Annotation> getConstraintClass();

    IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
            IProcessableElementTag elementTag);
}
