package integration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import static org.junit.Assert.*;

class HtmlChecker {

    private Document html;

    public HtmlChecker(Document html) {
        this.html = html;
    }

    ElementChecker elementWithId(String elementId) {
        Element element = null; //TODO DomUtils.getElementById(html, "input", elementId);
        assertNotNull("Element with id -" + elementId + "- not found", element);
        return new ElementChecker(element);
    }
}
