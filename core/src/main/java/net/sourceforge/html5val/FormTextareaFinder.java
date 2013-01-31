package net.sourceforge.html5val;

import org.thymeleaf.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class FormTextareaFinder {

    static List<Element> findTextAreas(Element form) {
        List<Element> textareas = DomUtils.getElementsByTagName(form, "textarea");
        List<Element> validTextAreas = new ArrayList<Element>();
        for (Element textarea : textareas) {
            addTextarea(validTextAreas, textarea);
        }
        return textareas;
    }

    private static void addTextarea(List<Element> validTextAreas, Element element) {
        if (hasNotEmptyName(element)) {
            validTextAreas.add(element);
        }
    }

    private static boolean hasNotEmptyName(Element element) {
        String name = element.getAttributeValue("name");
        return EmptyChecker.notEmpty(name);
    }
}