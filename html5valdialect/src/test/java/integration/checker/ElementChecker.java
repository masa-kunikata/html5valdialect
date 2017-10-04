package integration.checker;

import static org.junit.Assert.*;

import org.w3c.dom.Element;

public class ElementChecker {

    private Element element;

    public ElementChecker(Element element) {
        this.element = element;
    }

    public void containsAttributeWithValue(String attributeName, String attributeValue) {
        String errorMsg = String.format("Element with id -%s- does not contain attribute -%s- with value -%s-",
                element.getAttribute("id"), attributeName, attributeValue);
        assertTrue(errorMsg, attributeValue.equals(element.getAttribute(attributeName)));
    }
}
