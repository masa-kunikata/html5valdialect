package net.sourceforge.html5val;

import org.thymeleaf.dom.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.sourceforge.html5val.EmptyChecker.empty;
import static net.sourceforge.html5val.EmptyChecker.notEmpty;

/**
 * Some utility methods for managing DOM objects.
 */
public class DomUtils {

    /**
     * Get the unique element with the provided id.
     *
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
     * Get the list of elements with the provided name.
     */
    public static List<Element> getElementsByTagName(NestableNode element, String tagName) {
        DOMSelector selector = new DOMSelector("//" + tagName);
        return (List<Element>) (List) selector.select(element.getChildren());
    }

    /**
     * Given an element, return all descendants with provided tag names, in order of appearance.
     */
    public static List<Element> getElementsByTagNames(NestableNode parent, String... tagNames) {
        List<Element> result = new ArrayList<Element>();
        List<Element> children = parent.getElementChildren();
        if (!children.isEmpty()) {
            List<String> tagNamesList = Arrays.asList(tagNames);
            for (Element child : children) {
                if (startsWith(child.getOriginalName(), tagNamesList)) {
                    result.add(child);
                }
                result.addAll(getElementsByTagNames(child, tagNames));
            }
        }
        return result;
    }

    /**
     * Check if targetString starts with any of the searchStrings
     *
     * @param targetString  string to search in
     * @param searchStrings sequences to search for
     */
    private static boolean startsWith(String targetString, List<String> searchStrings) {
        if (notEmpty(targetString)) {
            for (String searchString : searchStrings) {
                if (targetString.startsWith(searchString)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Finds the immediatly previous DOM element for an element with a given type name
     */
    public static Element findPreviousElement(Element element, String elementName) {
        List<Element> siblings = element.getParent().getElementChildren();
        int currentIndex = siblings.indexOf(element);
        for (int i = currentIndex - 1; i >= 0; i--) {
            Element sibling = siblings.get(i);
            if (sibling.getNormalizedName().equals(elementName)) {
                return sibling;
            }
        }
        throw new IllegalArgumentException("No previous " + elementName + " element has been found");
    }

    /**
     * Remove provided element from DOM.
     */
    public static void removeElement(Element element) {
        element.clearChildren();
        element.getParent().removeChild(element);
    }
}
