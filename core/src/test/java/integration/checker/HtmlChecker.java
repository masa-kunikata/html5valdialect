package integration.checker;

import static org.junit.Assert.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class HtmlChecker {

    private Document html;

    public HtmlChecker(Document html) {
        this.html = html;
    }

    public ElementChecker elementWithId(String elementId) {
        Element element = html.getElementById(elementId);
        assertNotNull("Element with id -" + elementId + "- not found", element);
        return new ElementChecker(element);
    }
}
