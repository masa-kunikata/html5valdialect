package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotationExtractor {

    private Class targetClass;

    private AnnotationExtractor(Class annotatedClass) {
        this.targetClass = annotatedClass;
    }

    public static AnnotationExtractor forClass(Class annotatedClass) {
        return new AnnotationExtractor(annotatedClass);
    }

    /**
     * Return the annotations forClass a class field.
     */
    public List<Annotation> getAnnotationsFor(String fieldName) {
        if (fieldName == null) {
            return new ArrayList<Annotation>();
        }
        return fieldAnnotations(fieldName);
    }

    /**
     * Return annotations for a given field name
     */
    private List<Annotation> fieldAnnotations(String fieldname) {
        try {
            if (isSuperclassField(fieldname)) {
                return annotations(targetClass.getSuperclass(), fieldname);
            } else {
                return annotations(targetClass, fieldname);
            }
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Field not found in class" + targetClass.getName()
                    + ", nor superclass: " + targetClass.getSuperclass().getName() + ": " + fieldname);
        }
    }

    private boolean isClassField(Class aClass, String fieldname) {
        for (Field field : aClass.getDeclaredFields()) {
            if (field.getName().equals(fieldname)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSuperclassField(String fieldname) {
        return isClassField(targetClass.getSuperclass(), fieldname);
    }

    private List<Annotation> annotations(Class aClass, String fieldname) throws NoSuchFieldException {
        return Arrays.asList(aClass.getDeclaredField(fieldname).getAnnotations());
    }

}
