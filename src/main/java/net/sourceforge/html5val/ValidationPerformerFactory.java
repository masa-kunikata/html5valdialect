package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.html5val.performers.DigitsPerformer;
import net.sourceforge.html5val.performers.EmailPerformer;
import net.sourceforge.html5val.performers.MaxPerformer;
import net.sourceforge.html5val.performers.MinPerformer;
import net.sourceforge.html5val.performers.NotEmptyPerformer;
import net.sourceforge.html5val.performers.NotNullPerformer;
import net.sourceforge.html5val.performers.PatternPerformer;
import net.sourceforge.html5val.performers.SizePerformer;
import org.thymeleaf.dom.Element;

public class ValidationPerformerFactory {

    private static final ValidationPerformer NULL_PERFORMER = new ValidationPerformer() {
        public Class getConstraintClass() { return null; }
        public void putValidationCodeInto(Element element) {}
    };

    private final List<ValidationPerformer> performers;

    public ValidationPerformerFactory() {
        // TODO: read http://www.the-art-of-web.com/html/html5-form-validation/ for ideas
        performers = new ArrayList<ValidationPerformer>();
        performers.add(new DigitsPerformer());
        performers.add(new EmailPerformer());
        performers.add(new MaxPerformer());
        performers.add(new MinPerformer());
        performers.add(new NotEmptyPerformer());
        performers.add(new NotNullPerformer());
        performers.add(new PatternPerformer());
        performers.add(new SizePerformer());
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
        Class constraintClass = constraint.getClass();
        return constraintClass.isAssignableFrom(performer.getClass());
    }
}
