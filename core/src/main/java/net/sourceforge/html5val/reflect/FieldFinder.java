package net.sourceforge.html5val.reflect;

import java.lang.reflect.Field;

public class FieldFinder {

    public Field findField(Class targetClass, String targetFieldName) {
        if (isCompositeField(targetFieldName)) {
            return compositeField(targetClass, targetFieldName);
        } else {
            return fieldInClassOrSuperclass(targetClass, targetFieldName);
        }
    }

    /**
     * Return a field given its name.
     * Return null if the given field name doesn't match any of the class or superclass fields.
     */
    private Field fieldInClassOrSuperclass(Class type, String fieldName) {
        Field field = getField(type, fieldName);
        if (field != null) {
            return field;
        }
        if (type.getSuperclass() != null) {
            return fieldInClassOrSuperclass(type.getSuperclass(), fieldName);
        }
        return null;
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
     * Given a composite field with dot notation, return the last field referenced.
     * Let Foo class:
     *    <code>
     *        Class Foo {
     *          Bar bar;
     *        }
     *    </code>
     *
     * Let Bar class:
     *    <code>
     *        Class Bar {
     *            Object doo;
     *        }
     *    </code>
     *
     *  Using dot notation 'attribute.field' to reference the field 'doo' in Bar (bar.doo), given Foo this method returns
     *  the field 'doo' recursively.
     *
     *  Note that the field 'doo' could be in a superclass of Bar.
     */
    private Field compositeField(Class type, String compositeFieldName) {
        if (isCompositeField(compositeFieldName)) {
            String attribute = getParentField(compositeFieldName);
            Field field = fieldInClassOrSuperclass(type, attribute);
            if (field != null) {
                return compositeField(field.getType(), getField(compositeFieldName));
            } else {
                return null;
            }
        } else {
            return fieldInClassOrSuperclass(type, compositeFieldName);
        }
    }

    private String getField(String fieldName) {
        int dotPos = fieldName.indexOf('.');
        return fieldName.substring(dotPos + 1);
    }

    private String getParentField(String fieldName) {
        int dotPos = fieldName.indexOf('.');
        return fieldName.substring(0, dotPos);
    }

    private boolean isCompositeField(String fieldName) {
        return fieldName.indexOf('.') > 0;
    }
}
