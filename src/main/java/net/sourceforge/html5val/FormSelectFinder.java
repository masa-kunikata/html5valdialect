package net.sourceforge.html5val;

import org.thymeleaf.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class FormSelectFinder {

    private List<Element> validSelects = new ArrayList<Element>();

    List<Element> findSelects(Element form) {
        List<Element> selects = DomUtils.getElementsByTagName(form, "select");
        for (Element textarea : selects) {
            addTextarea(textarea);
        }
        return validSelects;
    }

    private void addTextarea(Element textarea) {
        if (hasNotEmptyName(textarea)) {
            validSelects.add(textarea);
        }
    }

    private boolean hasNotEmptyName(Element textarea) {
        String name = textarea.getAttributeValue("name");
        return EmptyChecker.notEmpty(name);
    }
}