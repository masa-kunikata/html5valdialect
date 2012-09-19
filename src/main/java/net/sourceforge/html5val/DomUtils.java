package net.sourceforge.html5val;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import org.thymeleaf.dom.DOMSelector;
import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import static net.sourceforge.html5val.DialectUtil.empty;

/**
 * Some utility methods for managing documents and Javascript.
 */
public class DomUtils {

    public static final String DOCUMENT_READY_BLOCK_ID = "onDocumentReadyBlockId";

    /**
     * Get the unique element with the provided id.
     * @return null if element is not found.
     * @throws IllegalStateException if more than one element are found.
     */
    public static Element getElementById(Document document, String elementName, String id) {
        DOMSelector selector = new DOMSelector("//" + elementName + "[@id=\"" + id + "\"]");
        List<Node> nodes = selector.select(document.getChildren());
        if (empty(nodes)) {
            return null;
        } else if (nodes.size() > 1) {
            throw new IllegalStateException("More than one element with id -" + id + "- found");
        } else {
            return (Element) nodes.get(0);
        }
    }

    /**
     * Get the first element with the provided id.
     * This method has worse performance than getElementById(document, elementName, id) method.
     * @return null if element is not found.
     */
    public static Element getFirstElementById(NestableNode parent, String id) {
        if (id == null) {
            return null;
        }
        for (Element child : parent.getElementChildren()) {
            if (id.equals(child.getAttributeValue("id"))) {
                return child;
            } else if (child.hasChildren()) {
                Element result = getFirstElementById(child, id);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * Get the list of elements with the provided name.
     */
    public static List<Element> getElementsByTagName(NestableNode element, String tagName) {
        DOMSelector selector = new DOMSelector("//" + tagName);
        return (List<Element>) (List) selector.select(element.getChildren());
    }

    /**
     * Given an element, return all descendants with provided tag names, in order of appearance.
     */
    public static List<Element> getElementsByTagNames(NestableNode parent, String ... tagNames) {
        List<Element> result = new ArrayList<Element>();
        List<Element> children = parent.getElementChildren();
        if (!children.isEmpty()) {
            List<String> tagNamesList = Arrays.asList(tagNames);
            for (Element child : children) {
                if (DialectUtil.startsWith(child.getOriginalName(), tagNamesList)) {
                    result.add(child);
                }
                result.addAll(getElementsByTagNames(child, tagNames));
            }
        }
        return result;
    }

    // FIXME: unit test this
    /**
     * Get id from provided element, or in absence create it.
     * @param document Full document
     * @param element Element to get or create id
     * @param idPrefix In case of creating id, prefix for id
     * @return element id (existent or created)
     */
    public static String getOrCreateId(Document document, Element element, String idPrefix) {
        if (element.hasAttribute("id")) {
            return element.getAttributeValue("id");
        } else {
            String newId = idPrefix;
            int id = 0;
            // Search for an unique id
            do {
                id++;
                newId = idPrefix + id;
            } while (DomUtils.getFirstElementById(document, newId) != null);
            element.setAttribute("id", newId);
            return newId;
        }
    }


    /**
     * Get all CSS classes from a Node.
     */
    // FIXME: unit test this
    public static Set<String> getClassNames(Element element) {
        Set<String> classNames = new HashSet<String>();
        if (element.hasAttribute("class")) {
            StringTokenizer st = new StringTokenizer(element.getAttributeValue("class"), " ");
            while (st.hasMoreTokens()) {
                classNames.add(st.nextToken());
            }
        }
        return classNames;
    }

    /**
     * Add an attribute value to an element.
     * If attribute value exists, add new value separated by an space.
     */
    // FIXME: unit test this
    public static void addAttributeValue(Element element, String attributeName, String newValue) {
        String attributeValue = newValue;
        if (element.hasAttribute(attributeName)) {
            attributeValue += " " + element.getAttributeValue(attributeName);
        }
        element.setAttribute(attributeName, attributeValue);
    }

    // FIXME: unit test this
    /**
     * Adds some code to jQuery "on document ready" block.
     * If the block does not exist previously, create it.
     */
    public static void addOnDocumentReady(Document document, String code) {
        // Create or locate script
        Element script = DomUtils.getElementById(document, "script", DOCUMENT_READY_BLOCK_ID);
        if (script == null) {
            script = new Element("script");
            script.setAttribute("type", "text/javascript");
            script.setAttribute("id", DOCUMENT_READY_BLOCK_ID);
            Text text = new Text("\n//<![CDATA[\n$(document).ready(function(){\n});\n//]]>\n", false);
            script.addChild(text);
            DomUtils.getElementsByTagName(document, "body").get(0).addChild(script);
        }
        // Build indented code
        StringBuilder codeSb = new StringBuilder();
        StringTokenizer lines = new StringTokenizer(code, "\n");
        while (lines.hasMoreTokens()) {
            codeSb.append("\t").append(lines.nextToken()).append("\n");
        }
        codeSb.append("");
        // Add new code to existent script text
        StringBuilder scriptSb = new StringBuilder(((Text) script.getFirstChild()).getContent());
        scriptSb.delete(scriptSb.length() - "});\n//]]>\n".length(), scriptSb.length());
        scriptSb.append(codeSb.toString());
        scriptSb.append("});\n//]]>\n");
        ((Text) script.getFirstChild()).setContent(scriptSb.toString());
    }
}
