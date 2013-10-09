package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
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
    public static List<Annotation> fieldAnnotations(Class classWithField, String field)
            throws NoSuchFieldException {
        return Arrays.asList(classWithField.getDeclaredField(field).getAnnotations());
    }
}