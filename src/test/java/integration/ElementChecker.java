package integration;

import org.thymeleaf.dom.Element;
import static org.junit.Assert.*;

class ElementChecker {

    private Element element;

    public ElementChecker(Element element) {
        this.element = element;
    }

    public void containsAttributeWithValue(String attributeName, String attributeValue) {
        String errorMsg = String.format("Element with id -%s- does not contain attribute -%s- with value -%s-", element.getAttributeValue("id"), attributeName, attributeValue);
        assertTrue(errorMsg, attributeValue.equals(element.getAttributeValue(attributeName)));
    }
}
