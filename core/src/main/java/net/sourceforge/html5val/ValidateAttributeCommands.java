package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IProcessableElementTag;

import net.sourceforge.html5val.performers.IValidationPerformer;
import net.sourceforge.html5val.performers.ValidationPerformerFactory;
import net.sourceforge.html5val.reflect.AnnotationExtractor;
import net.sourceforge.html5val.thymeleaf3.ExpressionUtils;
import net.sourceforge.html5val.thymeleaf3.FormElementFinders;

/**
 *
 */
enum ValidateAttributeCommands implements IValidateAttribute{
	/** */
	DEFAULT{
		@Override
		public void execute(final ITemplateContext context, final IModel model, final String attributeValue) {
			final Class<?> jsr303AnnotatedClass = readJsr303AnnotatedClass(context, attributeValue);
			processFields(context, jsr303AnnotatedClass, model);
		}

	},
	;

	protected Class<?> readJsr303AnnotatedClass(final ITemplateContext context, final String attributeValue) {
		return ExpressionUtils.evaluate(context, attributeValue).getClass();
	}

	protected void processFields(final ITemplateContext context, final Class<?> jsr303AnnotatedClass, final IModel model) {
		Map<Integer, IProcessableElementTag> tags =  FormElementFinders.findFormElements(model);
		for (Map.Entry<Integer, IProcessableElementTag> tag : tags.entrySet()) {
			IProcessableElementTag modifiedTag = processFieldValidation(context, jsr303AnnotatedClass, tag.getValue());
			model.replace(tag.getKey(), modifiedTag);
		}
	}

	protected IProcessableElementTag processFieldValidation(final ITemplateContext context, final Class<?> jsr303AnnotatedClass, final IProcessableElementTag elementTag) {
		IProcessableElementTag modifiedTag = elementTag;
		final String fieldName = getFieldName(modifiedTag);
		final List<? extends Annotation> constraints = AnnotationExtractor.forClass(jsr303AnnotatedClass).getAnnotationsForField(fieldName);
		for (final Annotation constraint : constraints) {
			IValidationPerformer performer = ValidationPerformerFactory.getPerformerFor(constraint);
			modifiedTag = performer.toValidationTag(constraint, context, modifiedTag);
		}
		return modifiedTag;
	}

	protected static final String TH_FIELD = "th:field"; // FIXME: get dynamic prefix (of Standard Dialect)

	protected String getFieldName(final IProcessableElementTag elementTag) {
		if (elementTag.getAttributeValue("name") != null) {
			return elementTag.getAttributeValue("name");
		} else if (elementTag.getAttributeValue(TH_FIELD) != null && elementTag.getAttributeValue(TH_FIELD).startsWith("*")) {
			String value = elementTag.getAttributeValue(TH_FIELD);
			return value.substring(2, value.length() - 1);
		}
		return null;
	}
}
