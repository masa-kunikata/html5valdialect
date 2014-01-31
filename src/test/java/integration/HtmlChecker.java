package integration;

import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;
import net.sourceforge.html5val.DomUtils;
import static org.junit.Assert.*;

class HtmlChecker {

    private Document html;

    public HtmlChecker(Document html) {
        this.html = html;
    }

    ElementChecker elementWithId(String elementId) {
        Element element = DomUtils.getElementById(html, "input", elementId);
        assertNotNull("Element with id -" + elementId + "- not found", element);
        return new ElementChecker(element);
    }
}
