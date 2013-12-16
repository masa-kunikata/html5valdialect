package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReflectionUtil {

    /**
     * Check whether a given field belong to a given class
     */
    public static boolean isClassField(Class aClass, String fieldName) {
        for (Field field : aClass.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return annotations for a given field name
     */
    public static List<Annotation> fieldAnnotations(Class classWithField, String field) {
        Field declaredField = declaredFieldOrNull(classWithField, field);
        if (declaredField != null) {
            return Arrays.asList(declaredField.getAnnotations());
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * Return a field given its name.
     * Return null if the given field name doesn't match any of the class fields.
     */
    public static Field declaredFieldOrNull(Class clazz, String fieldName) {
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }
}