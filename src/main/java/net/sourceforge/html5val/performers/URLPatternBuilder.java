 
package net.sourceforge.html5val.performers;

import java.util.regex.Pattern;
import org.hibernate.validator.constraints.URL;

public class URLPatternBuilder {
    
    private final String DEFAULT_REGEXP = ".*";
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
        sb.append("^(");
        sb.append(url.protocol()).append("://").append(url.host()).append(":").append(url.port()).append("/");
        sb.append(")");
        return sb.toString();
    }
    
}
