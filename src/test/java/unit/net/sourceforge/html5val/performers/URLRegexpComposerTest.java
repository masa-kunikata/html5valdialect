package unit.net.sourceforge.html5val.performers;

import net.sourceforge.html5val.performers.URLRegexpComposer;
import org.hibernate.validator.constraints.URL;
import org.junit.Test;
import static org.junit.Assert.*;
import static unit.util.RegexpMatcher.matchesRegexp;
import static unit.util.RegexpMismatcher.doesNotMatchRegexp;

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
        assertThat("://www.ya.com/", doesNotMatchRegexp(pattern));
        assertThat("ftp://www.ya.com/", doesNotMatchRegexp(pattern));
        assertThat("http://www.ya.com", matchesRegexp(pattern));
        assertThat("http://www.ya.com:443", matchesRegexp(pattern));
        assertThat("http://www.ya.com:443/", matchesRegexp(pattern));
        String result = URLRegexpComposer.forURL(urlAnnotation).regexp();
        assertEquals(pattern, result);
     }

    @Test
    public void annotationWithHost() {
        URL urlAnnotation = new MockURLBuilder().withHost("www.ya.com").build();
        String pattern = "^.+://www.ya.com(:[0-9]+)?(/.*)?";
        assertThat("://www.ya.com/", doesNotMatchRegexp(pattern));
        assertThat("http://www.google.com:443/", doesNotMatchRegexp(pattern));
        assertThat("http://www.ya.com443", doesNotMatchRegexp(pattern));
        assertThat("http://www.ya.com:abc", doesNotMatchRegexp(pattern));
        assertThat("http://www.ya.com:443show.html", doesNotMatchRegexp(pattern));
        assertThat("http://www.ya.com", matchesRegexp(pattern));
        assertThat("http://www.ya.com/show.html", matchesRegexp(pattern));
        assertThat("http://www.ya.com:443", matchesRegexp(pattern));
        assertThat("http://www.ya.com:443/show.html", matchesRegexp(pattern));
        String result = URLRegexpComposer.forURL(urlAnnotation).regexp();
        assertEquals(pattern, result);
     }

    @Test
    public void annotationWithPort() {
        URL urlAnnotation = new MockURLBuilder().withPort(443).build();
        String pattern = "^.+://.+:443(/.*)?";
        assertThat("://www.google.com:443/", doesNotMatchRegexp(pattern));
        assertThat("http://:443/", doesNotMatchRegexp(pattern));
        assertThat("http://www.ya.com:443show.html", doesNotMatchRegexp(pattern));
        assertThat("http://www.ya.com:443", matchesRegexp(pattern));
        assertThat("http://www.google.com:443/", matchesRegexp(pattern));
        assertThat("http://www.ya.com:443/", matchesRegexp(pattern));
        assertThat("http://www.ya.com:443/show.html", matchesRegexp(pattern));
        String result = URLRegexpComposer.forURL(urlAnnotation).regexp();
        assertEquals(pattern, result);
     }

    @Test
    public void annotationWithProtocolHostAndPort() {
        URL urlAnnotation = new MockURLBuilder().withProtocol("http").withHost("www.google.com").withPort(8080).build();
        String pattern = "^http://www.google.com:8080(/.*)?";
        assertThat("http://www.google.com:8080/", matchesRegexp(pattern));
        assertThat("http://www.google.com:8080bill.html", doesNotMatchRegexp(pattern));
        assertThat("http://www.google.com:8080/showBill/234.html", matchesRegexp(pattern));
        String result = URLRegexpComposer.forURL(urlAnnotation).regexp();
        assertEquals(pattern, result);
     }
}

