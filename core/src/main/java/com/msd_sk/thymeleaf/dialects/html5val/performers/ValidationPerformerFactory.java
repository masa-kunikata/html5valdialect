package com.msd_sk.thymeleaf.dialects.html5val.performers;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ValidationPerformerFactory {

    private static final ValidationPerformerFactory SINGLE_INSTANCE = new ValidationPerformerFactory();

    private final List<IValidationPerformer> performers;

    private ValidationPerformerFactory() {
        performers = Collections.synchronizedList(new ArrayList<IValidationPerformer>());
        performers.addAll(Arrays.asList(DefaultPerformers.values()));
    }

    /**
     * Add a custom IValidationPerformer to the list of performers.
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
        return DefaultPerformers.__NULL_PERFORMER;
    }

    private boolean isPerformerForConstraint(IValidationPerformer performer, Annotation constraint) {
        Class<?> constraintClass = constraint.getClass();
        return performer.getConstraintClass().isAssignableFrom(constraintClass);
    }
}
