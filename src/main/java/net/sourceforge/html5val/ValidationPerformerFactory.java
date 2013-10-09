package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sourceforge.html5val.performers.DigitsPerformer;
import net.sourceforge.html5val.performers.EmailPerformer;
import net.sourceforge.html5val.performers.LengthPerformer;
import net.sourceforge.html5val.performers.MaxPerformer;
import net.sourceforge.html5val.performers.MinPerformer;
import net.sourceforge.html5val.performers.NotBlankPerformer;
import net.sourceforge.html5val.performers.NotEmptyPerformer;
import net.sourceforge.html5val.performers.NotNullPerformer;
import net.sourceforge.html5val.performers.PatternPerformer;
import net.sourceforge.html5val.performers.RangePerformer;
import net.sourceforge.html5val.performers.SizePerformer;
import net.sourceforge.html5val.performers.URLPerformer;
import org.thymeleaf.dom.Element;

public class ValidationPerformerFactory {

    private static final ValidationPerformerFactory SINGLE_INSTANCE = new ValidationPerformerFactory();

    private static final ValidationPerformer NULL_PERFORMER = new ValidationPerformer() {
        public Class getConstraintClass() { return null; }
        public void putValidationCodeInto(Annotation constraint, Element element) {}
    };

    private final List<ValidationPerformer> performers;

    private ValidationPerformerFactory() {
        performers = Collections.synchronizedList(new ArrayList<ValidationPerformer>());
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
    public static void addCustomPerformer(ValidationPerformer performer) {
        SINGLE_INSTANCE.performers.add(performer);
    }

    public static ValidationPerformer getPerformerFor(Annotation constraint) {
        return SINGLE_INSTANCE.getPerformerForConstraint(constraint);
    }

    private ValidationPerformer getPerformerForConstraint(Annotation constraint) {
        for (ValidationPerformer performer : performers) {
            if (isPerformerForConstraint(performer, constraint)) {
                return performer;
            }
        }
        return NULL_PERFORMER;
    }

    private boolean isPerformerForConstraint(ValidationPerformer performer, Annotation constraint) {
        Class constraintClass = constraint.getClass();
        return performer.getConstraintClass().isAssignableFrom(constraintClass);
    }
}
