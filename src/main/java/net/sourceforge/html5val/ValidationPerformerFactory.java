package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.html5val.performers.NotEmptyPerformer;
import org.thymeleaf.dom.Element;

public class ValidationPerformerFactory {

    private static final ValidationPerformer NULL_PERFORMER = new ValidationPerformer() {
        public Class getConstraintClass() { return null; }
        public void putValidationCodeInto(Element element) {}
    };

    private final List<ValidationPerformer> performers;

    public ValidationPerformerFactory() {
        performers = new ArrayList<ValidationPerformer>();
        performers.add(new NotEmptyPerformer());
    }

    public ValidationPerformer getProcessorFor(Annotation constraint) {
        for (ValidationPerformer performer : performers) {
            if (isPerformerForConstraint(performer, constraint)) {
                return performer;
            }
        }
        return NULL_PERFORMER;
    }

    private boolean isPerformerForConstraint(ValidationPerformer performer, Annotation constraint) {
        return constraint.getClass().equals(performer.getConstraintClass());
    }
}
