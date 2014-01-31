package unit.util;

import java.util.regex.Pattern;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RegexpMismatcher extends TypeSafeMatcher<String> {

    private String regexp;

    private RegexpMismatcher(String regexp) {
        this.regexp = regexp;
    }

    public static RegexpMismatcher doesNotMatchRegexp(String regexp) {
        return new RegexpMismatcher(regexp);
    }

    @Override
    public boolean matchesSafely(String stringToMatch) {
        Pattern p = Pattern.compile(regexp);
        return !p.matcher(stringToMatch).matches();
    }

    public void describeTo(Description description) {
        description.appendText("String not matching regexp " + regexp);
    }
}
