package net.sourceforge.html5val;

import java.lang.annotation.Annotation;

public class AnnotationUtil {

    /**
     * Return the
     *
     * @Annotations of a class field. Supports dot-syntax for fieldName, i.e., "store.name" returns the annotations
     * for field "name" of field "store"
     */
    // FIXME: unit-test this
    // FIXME: what if the field name really contais a dot? what happens in spring?
    // FIXME: it ignores fields declared in parent classes
    public Annotation[] annotationsFor(Class clazz, String fieldName) {
        try {
            String currentField = fieldName;
            Class currentClass = clazz;
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
     * Check if a list of fields contains some field.
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
