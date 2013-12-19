package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static net.sourceforge.html5val.EmptyChecker.empty;
import static net.sourceforge.html5val.ReflectionUtil.fieldAnnotations;
import static net.sourceforge.html5val.ReflectionUtil.isClassField;

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
    public List<Annotation> getAnnotationsFor(String fieldName) {
        this.targetFieldName = fieldName;
        if (empty(targetFieldName)) {
            return new ArrayList<Annotation>();
        }
        Class classWithField = findClassWithField();
        String field = findTargetFieldName();
        return fieldAnnotations(classWithField, field);
    }

    /** Find field class by searching in the given class, its nested fields and its superclass */
    private Class findClassWithField() {
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

    /** A field is considered nested if its name contains at least one dot and is not an array fo values */
    private boolean isNestedField() {
        return targetFieldName.contains(".");
    }

    /** Given a nested field, this method finds its class searching in nested class fields */
    private Class findClassInFields() {
        Class matchingClass = null;
        Class currentClass = targetClass;
        String currentField = targetFieldName;
        while (currentField.indexOf('.') > 0) {
            int dotPos = currentField.indexOf('.');
            String compositeField = currentField.substring(0, dotPos);
            currentField = currentField.substring(dotPos + 1);
            // TODO Refactor
            String getter = "get" + compositeField.substring(0, 1).toUpperCase().
                    concat(compositeField.substring(1, compositeField.length()));
            for (Method method : currentClass.getMethods()) {
                if (method.getName().equals(getter)) {
                    currentClass = method.getReturnType();
                    matchingClass = currentClass;
                    break;
                }
            }
        }
        return matchingClass;
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
