package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.sourceforge.html5val.EmptyChecker.empty;

// TODO Refactor
public class AnnotationExtractor {

    private Class targetClass;
    private String targetFieldName;

    private AnnotationExtractor(Class annotatedClass) {
        this.targetClass = annotatedClass;
    }

    public static AnnotationExtractor forClass(Class annotatedClass) {
        return new AnnotationExtractor(annotatedClass);
    }

    /**
     * Return the annotations forClass a class field.
     */
    public List<Annotation> getAnnotationsForField(String fieldName) {
        this.targetFieldName = fieldName;
        if (empty(targetFieldName)) {
            return new ArrayList<Annotation>();
        }
        return fieldAnnotations();
    }

    /**
     * Return annotations for a given field name
     */
    private List<Annotation> fieldAnnotations() {
        Field field = findField();
        if (field != null) {
            return Arrays.asList(field.getAnnotations());
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private Field findField() {
        if (isCompositeField()) {
            return compositeField(targetClass, targetFieldName);
        } else {
            return declaredFieldInClassOrSuperclass(targetClass, targetFieldName);
        }
    }

    private boolean isCompositeField() {
        return targetFieldName.contains(".");
    }

    private Field compositeField(Class type, String fieldName) {
        if (fieldName.indexOf('.') > 0) {
            int dotPos = fieldName.indexOf('.');
            String attribute = fieldName.substring(0, dotPos);
            String field = fieldName.substring(dotPos + 1);
            Class attributeType = getField(type, attribute).getType();
            return compositeField(attributeType, field);
        } else {
            return declaredFieldInClassOrSuperclass(type, fieldName);
        }
    }

    private Field getField(Class type, String attribute) {
        for (Field classField : type.getDeclaredFields()) {
            if (classField.getName().equals(attribute)) {
                return classField;
            }
        }
        return null;
    }

    /**
     * Return a field given its name.
     * Return null if the given field name doesn't match any of the class fields.
     */
    private Field declaredFieldInClassOrSuperclass(Class type, String targetFieldName) {
        Field field = getField(type, targetFieldName);
        if (field != null) {
            return field;
        }
        if (type.getSuperclass() != null) {
            return declaredFieldInClassOrSuperclass(type.getSuperclass(), targetFieldName);
        }
        return null;
    }
}
