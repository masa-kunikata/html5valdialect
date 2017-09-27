package net.sourceforge.html5val.thymeleaf3;

import org.thymeleaf.dom.Element;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.html5val.util.EmptyChecker;

public class FormTextareaFinder {

    private List<Element> validTextAreas = new ArrayList<Element>();

    List<Element> findTextAreas(Element form) {
        List<Element> textareas = DomUtils.getElementsByTagName(form, "textarea");
        for (Element textarea : textareas) {
            addTextarea(textarea);
        }
        return textareas;
    }

    private void addTextarea(Element element) {
        if (hasNotEmptyName(element)) {
            validTextAreas.add(element);
        }
    }

    private boolean hasNotEmptyName(Element element) {
        String name = element.getAttributeValue("name");
        return EmptyChecker.notEmpty(name);
    }
}