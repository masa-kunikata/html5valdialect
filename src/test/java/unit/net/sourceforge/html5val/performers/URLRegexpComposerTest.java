package unit.net.sourceforge.html5val.performers;

import java.util.regex.Pattern;
import net.sourceforge.html5val.performers.URLRegexpComposer;
import org.hibernate.validator.constraints.URL;
import org.junit.Test;
import static org.junit.Assert.*;

public class URLRegexpComposerTest {

    private static final String REGEXP = "regexp";

    @Test
    public void annotationWithPattern() {
        URL urlAnnotation = new MockURLBuilder().withRegexp(REGEXP).build();
        assertEquals(REGEXP, URLRegexpComposer.forURL(urlAnnotation).regexp());
     }

    @Test
    public void annotationWithProtocol() {
        URL urlAnnotation = new MockURLBuilder().withProtocol("http").build();
        String pattern = "^http://.+(:[0-9]+)?(/.*)?";
        checkRegexpNotMatches(pattern, "://www.ya.com/");
        checkRegexpNotMatches(pattern, "ftp://www.ya.com/");
        checkRegexpMatches(pattern, "http://www.ya.com");
        checkRegexpMatches(pattern, "http://www.ya.com:443");
        checkRegexpMatches(pattern, "http://www.ya.com:443/");
        String result = URLRegexpComposer.forURL(urlAnnotation).regexp();
        assertEquals(pattern, result);
     }

    @Test
    public void annotationWithHost() {
        URL urlAnnotation = new MockURLBuilder().withHost("www.ya.com").build();
        String pattern = "^.+://www.ya.com(:[0-9]+)?(/.*)?";
        checkRegexpNotMatches(pattern, "://www.ya.com/");
        checkRegexpNotMatches(pattern, "http://www.google.com:443/");
        checkRegexpNotMatches(pattern, "http://www.ya.com443");
        checkRegexpNotMatches(pattern, "http://www.ya.com:abc");
        checkRegexpNotMatches(pattern, "http://www.ya.com:443show.html");
        checkRegexpMatches(pattern, "http://www.ya.com");
        checkRegexpMatches(pattern, "http://www.ya.com/show.html");
        checkRegexpMatches(pattern, "http://www.ya.com:443");
        checkRegexpMatches(pattern, "http://www.ya.com:443/show.html");
        String result = URLRegexpComposer.forURL(urlAnnotation).regexp();
        assertEquals(pattern, result);
     }

    @Test
    public void annotationWithPort() {
        URL urlAnnotation = new MockURLBuilder().withPort(443).build();
        String pattern = "^.+://.+:443(/.*)?";
        checkRegexpNotMatches(pattern, "://www.google.com:443/");
        checkRegexpMatches(pattern, "http://www.ya.com:443");
        checkRegexpMatches(pattern, "http://www.google.com:443/");
        checkRegexpMatches(pattern, "http://www.ya.com:443/");
        checkRegexpNotMatches(pattern, "http://:443/");
        checkRegexpNotMatches(pattern, "http://www.ya.com:443show.html");
        checkRegexpMatches(pattern, "http://www.ya.com:443/show.html");
        String result = URLRegexpComposer.forURL(urlAnnotation).regexp();
        assertEquals(pattern, result);
     }

    @Test
    public void annotationWithProtocolHostAndPort() {
        URL urlAnnotation = new MockURLBuilder().withProtocol("http").withHost("www.google.com").withPort(8080).build();
        String pattern = "^http://www.google.com:8080(/.*)?";
        checkRegexpMatches(pattern, "http://www.google.com:8080/");
        checkRegexpNotMatches(pattern, "http://www.google.com:8080bill.html");
        checkRegexpMatches(pattern, "http://www.google.com:8080/showBill/234.html");
        String result = URLRegexpComposer.forURL(urlAnnotation).regexp();
        assertEquals(pattern, result);
     }

    private void checkRegexpMatches(String regexp, String stringToMatch) {
        Pattern p = Pattern.compile(regexp);
        assertTrue(String.format("%s does not match regexp %s", stringToMatch, regexp),
            p.matcher(stringToMatch).matches());
    }

    private void checkRegexpNotMatches(String regexp, String stringToMatch) {
        Pattern p = Pattern.compile(regexp);
        assertFalse(String.format("%s should not match regexp %s", stringToMatch, regexp),
            p.matcher(stringToMatch).matches());
    }
}

