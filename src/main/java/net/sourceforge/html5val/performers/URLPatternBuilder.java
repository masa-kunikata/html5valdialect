
package net.sourceforge.html5val.performers;

import java.util.regex.Pattern;
import org.hibernate.validator.constraints.URL;
import static net.sourceforge.html5val.EmptyChecker.empty;

public class URLPatternBuilder {

    public static final String DEFAULT_REGEXP = ".*";
    public static final int EMPTY_PORT = -1;
    private URL url;

    private URLPatternBuilder(URL url) {
        this.url = url;
    }

    public static URLPatternBuilder forURL(URL url) {
        return new URLPatternBuilder(url);
    }

    public String getPattern() {
        if (containsRegexp()) {
            return url.regexp();
        } else {
            return buildPattern();
        }
    }

    private boolean containsRegexp() {
        // URL regexp is never empty and by default is ".*"
        return !DEFAULT_REGEXP.equals(url.regexp());
    }

    private String buildPattern() {
        String urlRegex = buildRegex();
        Pattern urlPattern = Pattern.compile(urlRegex);
        return urlPattern.pattern();
    }

    private String buildRegex() {
        StringBuilder sb = new StringBuilder();
        sb.append("^");
        if (empty(url.protocol())) {
            sb.append(".+");
        } else {
            sb.append(url.protocol());
        }
        sb.append("://");
        if (empty(url.host())) {
            sb.append(".+");
        } else {
            sb.append(url.host());
        }
        if (url.port() == EMPTY_PORT) {
            sb.append("(:[0-9]+)?");
        } else {
            sb.append(":").append(url.port());
        }
        sb.append("(/.*)?");
        return sb.toString();
    }

}
