package net.sourceforge.html5val.performers;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;

public class ValidationPerformerFactory {

    private static final ValidationPerformerFactory SINGLE_INSTANCE = new ValidationPerformerFactory();

    private static final IValidationPerformer NULL_PERFORMER = new IValidationPerformer() {
        public Class getConstraintClass() { return null; }
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context, IProcessableElementTag elementTag) {return null;}
    };

    private final List<IValidationPerformer> performers;

    private ValidationPerformerFactory() {
        performers = Collections.synchronizedList(new ArrayList<IValidationPerformer>());
        performers.add(new DigitsPerformer());
        performers.add(new EmailPerformer());
        performers.add(new MaxPerformer());
        performers.add(new MinPerformer());
        performers.add(new RangePerformer());
        performers.add(new NotEmptyPerformer());
        performers.add(new NotNullPerformer());
        performers.add(new NotBlankPerformer());
        performers.add(new PatternPerformer());
        performers.add(new SizePerformer());
        performers.add(new LengthPerformer());
        performers.add(new URLPerformer());
    }

    /**
     * Add a custom ValidationPerformer to the list of performers.
     */
    public static void addCustomPerformer(IValidationPerformer performer) {
        SINGLE_INSTANCE.performers.add(0, performer);
    }

    public static IValidationPerformer getPerformerFor(Annotation constraint) {
        return SINGLE_INSTANCE.getPerformerForConstraint(constraint);
    }

    private IValidationPerformer getPerformerForConstraint(Annotation constraint) {
        for (IValidationPerformer performer : performers) {
            if (isPerformerForConstraint(performer, constraint)) {
                return performer;
            }
        }
        return NULL_PERFORMER;
    }

    private boolean isPerformerForConstraint(IValidationPerformer performer, Annotation constraint) {
        Class constraintClass = constraint.getClass();
        return performer.getConstraintClass().isAssignableFrom(constraintClass);
    }
}
