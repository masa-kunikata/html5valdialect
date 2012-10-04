package unit.net.sourceforge.html5val.performers;

import java.util.regex.Pattern;
import net.sourceforge.html5val.performers.URLPatternBuilder;
import org.hibernate.validator.constraints.URL;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class URLPatternBuilderTest {

    private final Mockery context = new JUnit4Mockery();
    private URL urlAnnotation;
    private static final String REGEXP = "regexp";

    @Before
    public void createMock() {
        urlAnnotation = context.mock(URL.class);
    }

    @Test
    public void annotationWithPattern() {
        context.checking(new Expectations(){{
            allowing(urlAnnotation).regexp(); will(returnValue(REGEXP));
        }});
        assertEquals(REGEXP, URLPatternBuilder.forURL(urlAnnotation).getPattern());
     }

    @Test
    public void annotationWithProtocol() {
        context.checking(new Expectations(){{
            allowing(urlAnnotation).regexp(); will(returnValue(URLPatternBuilder.DEFAULT_REGEXP));
            allowing(urlAnnotation).protocol(); will(returnValue("http"));
            allowing(urlAnnotation).port(); will(returnValue(URLPatternBuilder.EMPTY_PORT));
            allowing(urlAnnotation);
        }});
        String pattern = "^http://.+(:[0-9]+)?(/.*)?";
        checkRegexpNotMatches(pattern, "://www.ya.com/");
        checkRegexpNotMatches(pattern, "ftp://www.ya.com/");
        checkRegexpMatches(pattern, "http://www.ya.com");
        checkRegexpMatches(pattern, "http://www.ya.com:443");
        checkRegexpMatches(pattern, "http://www.ya.com:443/");
        String result = URLPatternBuilder.forURL(urlAnnotation).getPattern();
        assertEquals(pattern, result);
     }

    @Test
    public void annotationWithHost() {
        context.checking(new Expectations(){{
            allowing(urlAnnotation).regexp(); will(returnValue(URLPatternBuilder.DEFAULT_REGEXP));
            allowing(urlAnnotation).host(); will(returnValue("www.ya.com"));
            allowing(urlAnnotation).port(); will(returnValue(URLPatternBuilder.EMPTY_PORT));
            allowing(urlAnnotation);
        }});
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
        String result = URLPatternBuilder.forURL(urlAnnotation).getPattern();
        assertEquals(pattern, result);
     }

    @Test
    public void annotationWithPort() {
        context.checking(new Expectations(){{
            allowing(urlAnnotation).regexp(); will(returnValue(URLPatternBuilder.DEFAULT_REGEXP));
            allowing(urlAnnotation).port(); will(returnValue(443));
            allowing(urlAnnotation);
        }});
        String pattern = "^.+://.+:443(/.*)?";
        checkRegexpNotMatches(pattern, "://www.google.com:443/");
        checkRegexpMatches(pattern, "http://www.ya.com:443");
        checkRegexpMatches(pattern, "http://www.google.com:443/");
        checkRegexpMatches(pattern, "http://www.ya.com:443/");
        checkRegexpNotMatches(pattern, "http://:443/");
        checkRegexpNotMatches(pattern, "http://www.ya.com:443show.html");
        checkRegexpMatches(pattern, "http://www.ya.com:443/show.html");
        String result = URLPatternBuilder.forURL(urlAnnotation).getPattern();
        assertEquals(pattern, result);
     }

    @Test
    public void annotationWithProtocolHostAndPort() {
        context.checking(new Expectations(){{
            allowing(urlAnnotation).regexp(); will(returnValue(URLPatternBuilder.DEFAULT_REGEXP));
            allowing(urlAnnotation).protocol(); will(returnValue("http"));
            allowing(urlAnnotation).host(); will(returnValue("www.google.com"));
            allowing(urlAnnotation).port(); will(returnValue(8080));
        }});
        String pattern = "^http://www.google.com:8080(/.*)?";
        checkRegexpMatches(pattern, "http://www.google.com:8080/");
        checkRegexpNotMatches(pattern, "http://www.google.com:8080bill.html");
        checkRegexpMatches(pattern, "http://www.google.com:8080/showBill/234.html");
        String result = URLPatternBuilder.forURL(urlAnnotation).getPattern();
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

