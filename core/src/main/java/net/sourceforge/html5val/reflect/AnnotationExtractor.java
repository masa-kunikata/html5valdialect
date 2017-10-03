package net.sourceforge.html5val.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sourceforge.html5val.util.EmptyChecker;

public class AnnotationExtractor {

    private Class<?> targetClass;
    private String targetFieldName;
    private FieldFinder fieldFinder = new FieldFinder();

    private AnnotationExtractor(Class<?> annotatedClass) {
        this.targetClass = annotatedClass;
    }

    public static AnnotationExtractor forClass(Class<?> annotatedClass) {
        return new AnnotationExtractor(annotatedClass);
    }

    /**
     * Return the annotations forClass a class field.
     */
    public List<Annotation> getAnnotationsForField(String fieldName) {
        this.targetFieldName = fieldName;
        if (EmptyChecker.empty(targetFieldName)) {
            return new ArrayList<Annotation>();
        }
        return fieldAnnotations();
    }

    /**
     * Return annotations for a given field name
     */
    private List<Annotation> fieldAnnotations() {
        Field field = fieldFinder.findField(targetClass, targetFieldName);
        if (field != null) {
            return Arrays.asList(field.getAnnotations());
        } else {
            return Collections.emptyList();
        }
    }
}
