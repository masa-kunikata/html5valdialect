package net.sourceforge.html5val;

import org.thymeleaf.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class FormSelectFinder {

    static List<Element> findSelects(Element form) {
        List<Element> selects = DomUtils.getElementsByTagName(form, "select");
        List<Element> validSelects = new ArrayList<Element>();
        for (Element textarea : selects) {
            addTextarea(validSelects, textarea);
        }
        return validSelects;
    }

    private static void addTextarea(List<Element> validSelects, Element textarea) {
        if (hasNotEmptyName(textarea)) {
            validSelects.add(textarea);
        }
    }

    private static boolean hasNotEmptyName(Element textarea) {
        String name = textarea.getAttributeValue("name");
        return EmptyChecker.notEmpty(name);
    }
}