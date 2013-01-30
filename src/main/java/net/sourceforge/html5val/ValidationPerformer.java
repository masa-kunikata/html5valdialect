package net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import org.thymeleaf.dom.Element;

public interface ValidationPerformer<T extends Annotation> {

    Class<T> getConstraintClass();

    void putValidationCodeInto(T constraint, Element element);
}
