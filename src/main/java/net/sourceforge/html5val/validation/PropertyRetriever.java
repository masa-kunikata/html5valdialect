package net.sourceforge.html5val.validation;

import java.lang.reflect.Field;

class PropertyRetriever {

    public static Object getProperty(Object object, String property) {
        try {
            Field field = object.getClass().getDeclaredField(property);
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException ex) {
            throw translateException(ex, property);
        } catch (NoSuchFieldException ex) {
            throw translateException(ex, property);
        }
    }

    private static RuntimeException translateException(Exception ex, String property) {
        return new IllegalArgumentException("Could not retrieve property: " + property, ex);
    }
}
