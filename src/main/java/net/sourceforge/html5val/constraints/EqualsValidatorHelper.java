package net.sourceforge.html5val.constraints;

import static net.sourceforge.html5val.constraints.PropertyRetriever.getProperty;

class EqualsValidatorHelper {

    private Equals annotation;

    private Object property1;

    private Object property2;

    private EqualsValidatorHelper(Object object, Equals annotation) {
        this.annotation = annotation;
        this.property1 = getProperty(object, annotation.property1());
        this.property2 = getProperty(object, annotation.property2());
    }

    public static boolean validate(Object object, Equals annotation) {
        EqualsValidatorHelper helper = new EqualsValidatorHelper(object, annotation);
        return helper.isValid();
    }

    public boolean isValid() {
        if (bothPropertiesAreNull()) {
            return true;
        }
        if (justOnePropertyIsNull()) {
            return false;
        }
        checkPropertiesType();
        return property1.equals(property2);
    }

    private boolean bothPropertiesAreNull() {
        return property1 == null && property2 == null;
    }

    private boolean justOnePropertyIsNull() {
        return (property1 == null && property2 != null) || (property1 != null && property2 == null);
    }

    private void checkPropertiesType() {
        if (propertiesHaveDifferentType()) {
            String errorMsg = String.format("-%s- and -%s- type must be the same", annotation.property1(), annotation.property2());
            throw new IllegalArgumentException(errorMsg);
        }
    }

    private boolean propertiesHaveDifferentType() {
        return !property1.getClass().equals(property2.getClass());
    }
}
