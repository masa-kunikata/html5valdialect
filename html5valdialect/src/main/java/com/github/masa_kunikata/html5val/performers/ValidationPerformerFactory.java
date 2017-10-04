package com.github.masa_kunikata.html5val.performers;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ValidationPerformerFactory {

    private static final ValidationPerformerFactory SINGLE_INSTANCE = new ValidationPerformerFactory();

    private final List<IValidationPerformer> performers;

    private ValidationPerformerFactory() {
        performers = new CopyOnWriteArrayList<IValidationPerformer>();
        performers.addAll(Arrays.asList(DefaultPerformers.values()));
    }

    /**
     * Add a custom IValidationPerformer to the list of performers.
     */
    public static void addCustomPerformer(IValidationPerformer performer) {
        SINGLE_INSTANCE.performers.add(0, performer);
    }

    /**
     * Remove a custom IValidationPerformer from the list of performers.
     */
    public static void removeCustomPerformer(IValidationPerformer performer) {
        if(Arrays.asList(DefaultPerformers.values()).contains(performer)) {
            return;
        }

        SINGLE_INSTANCE.performers.remove(performer);
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
