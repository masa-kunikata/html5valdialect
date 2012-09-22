package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
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

    private final Map<Class, ValidationPerformer> performerMap;

    public ValidationPerformerFactory() {
        // TODO: read http://www.the-art-of-web.com/html/html5-form-validation/ for ideas
        performerMap = new HashMap<Class, ValidationPerformer>();
        addPerformer(new DigitsPerformer());
        addPerformer(new EmailPerformer());
        addPerformer(new MaxPerformer());
        addPerformer(new MinPerformer());
        addPerformer(new NotEmptyPerformer());
        addPerformer(new NotNullPerformer());
        addPerformer(new PatternPerformer());
        addPerformer(new SizePerformer());
    }

    private void addPerformer(ValidationPerformer performer) {
        performerMap.put(performer.getClass(), performer);
    }

    public ValidationPerformer getProcessorFor(Annotation constraint) {
        Class constraintClass = constraint.getClass();
        if (performerMap.containsKey(constraintClass)) {
            return performerMap.get(constraintClass);
        }
        return NULL_PERFORMER;
    }
}
