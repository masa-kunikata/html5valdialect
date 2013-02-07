package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import static net.sourceforge.html5val.ReflectionUtil.fieldAnnotations;
import static net.sourceforge.html5val.ReflectionUtil.isClassField;

// FIXME Better refactoring is possible
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
    public List<Annotation> getAnnotationsFor(String fieldName) {
        this.targetFieldName = fieldName;
        if (targetFieldName == null) {
            return new ArrayList<Annotation>();
        }
        try {
            Class classWithField = findClassWithField();
            String field = findTargetFieldName();
            return fieldAnnotations(classWithField, field);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Field not found in class" + targetClass.getName()
                    + ", nor superclass: " + targetClass.getSuperclass().getName() + ": " + targetFieldName);
        }
    }

    /** Find field class by searching in the given class, its nested fields and its superclass */
    private Class findClassWithField() throws NoSuchFieldException {
        if (isNestedField()) {
            return findClassInFields();
        } else {
            if (isSuperClassField()) {
                return targetClass.getSuperclass();
            } else {
                return targetClass;
            }
        }
    }

    /** A field is considered nested if its name contains at least one dot */
    private boolean isNestedField() {
        return targetFieldName.contains(".");
    }

    /** Given a nested field, this method finds its class searching in nested class fields */
    private Class findClassInFields() throws NoSuchFieldException {
        Class currentClass = targetClass;
        String currentField = targetFieldName;
        while (currentField.indexOf('.') > 0) {
            int dotPos = currentField.indexOf('.');
            String compositeField = currentField.substring(0, dotPos);
            currentField = currentField.substring(dotPos + 1);
            if (!isClassField(currentClass, compositeField)) {
                return null;
            }
            currentClass = currentClass.getDeclaredField(compositeField).getType();
        }
        return currentClass;
    }

    private String findTargetFieldName() {
        if (isNestedField()) {
            return targetFieldName.substring(targetFieldName.lastIndexOf(".") + 1);
        }
        return targetFieldName;
    }

    private boolean isSuperClassField() {
        return isClassField(targetClass.getSuperclass(), targetFieldName);
    }
}
