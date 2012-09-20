package net.sourceforge.html5val;

import java.util.Collection;

public class EmptyChecker {

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
}
