package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import org.thymeleaf.dom.Element;

public class AnnotationExtractor {

    private Class annotatedClass;

    private AnnotationExtractor(Class annotatedClass) {
        this.annotatedClass = annotatedClass;
    }

    public static AnnotationExtractor forClass(Class annotatedClass) {
        return new AnnotationExtractor(annotatedClass);
    }

    /**
     * Return the annotations forClass a class field. Supports dot-syntax for fieldName, i.e., "store.name" returns the annotations
     * for field "name" forClass field "store"
     */
    // FIXME: unit-test this
    // FIXME: what if the field name really contais a dot? what happens in spring?
    // FIXME: it ignores fields declared in parent classes
    public Annotation[] getAnnotationsFor(Element field) {
        try {
            String fieldName = field.getAttributeValue("name");
            String currentField = fieldName;
            Class currentClass = annotatedClass;
            while (currentField.indexOf('.') > 0) {
                int dotPos = currentField.indexOf('.');
                String compositeField = currentField.substring(0, dotPos);
                currentField = currentField.substring(dotPos + 1);
                if (!containsField(currentClass.getDeclaredFields(), compositeField)) {
                    return new Annotation[0];
                }
                currentClass = currentClass.getDeclaredField(compositeField).getType();
            }
            if (containsField(currentClass.getDeclaredFields(), currentField)) {
                return currentClass.getDeclaredField(currentField).getAnnotations();
            } else {
                return new Annotation[0];
            }
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Check if a list forClass fields contains some field.
     */
    private boolean containsField(java.lang.reflect.Field[] fields, String fieldName) {
        for (java.lang.reflect.Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
