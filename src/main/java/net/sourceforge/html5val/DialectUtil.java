package net.sourceforge.html5val;

import java.util.Collection;
import java.util.List;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.WebContext;

public class DialectUtil {

    /** If the provided String is empty or null. */
    public static boolean empty(String str) {
        return str == null || str.length() == 0;
    }

    /** If the provided Collection is empty or null. */
    public static boolean empty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /** If the provided String is null, empty or blank. */
    public static boolean emptyOrBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /** If the provided String is not empty nor null. */
    public static boolean notEmpty(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * Check if targetString starts with any of the searchStrings
     * @param targetString string to search in
     * @param searchStrings sequences to search for
     */
    public static boolean startsWith(String targetString, List<String> searchStrings) {
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
     * Escape quotes for javascript usage.
     */
    public static String escapeQuotes(String text) {
        return text.replaceAll("\'", "\\\\\'").replaceAll("\"", "\\\\\"");
    }

    public static String getContextPath(Arguments arguments) {
        WebContext context = (WebContext) arguments.getContext();
        return context.getHttpServletRequest().getContextPath();
    }
}
