package net.sourceforge.html5val.performers;

import java.lang.annotation.Annotation;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;

public interface IValidationPerformer<T extends Annotation> {

    Class<T> getConstraintClass();

    IProcessableElementTag toValidationTag(T constraint, ITemplateContext context, IProcessableElementTag elementTag);
}
