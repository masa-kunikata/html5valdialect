package unit.util;

import java.util.regex.Pattern;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RegexpMatcher extends TypeSafeMatcher<String> {

    private String regexp;

    private RegexpMatcher(String regexp) {
        this.regexp = regexp;
    }

    public static RegexpMatcher matchesRegexp(String regexp) {
        return new RegexpMatcher(regexp);
    }

    @Override
    public boolean matchesSafely(String stringToMatch) {
        Pattern p = Pattern.compile(regexp);
        return p.matcher(stringToMatch).matches();
    }

    public void describeTo(Description description) {
        description.appendText("String matching regexp " + regexp);
    }
}
