package com.msd_sk.thymeleaf.dialects.html5val.performers.regexp_composer;

import org.hibernate.validator.constraints.URL;

import com.msd_sk.thymeleaf.dialects.html5val.util.EmptyChecker;

public class URLRegexpComposer implements IRegexpComposer {

    public static final String DEFAULT_REGEXP = ".*";
    public static final int EMPTY_PORT = -1;
    private URL url;

    private URLRegexpComposer(URL url) {
        this.url = url;
    }

    public static URLRegexpComposer forURL(URL url) {
        return new URLRegexpComposer(url);
    }

    @Override
    public String regexp() {
        if (containsRegexp()) {
            return url.regexp();
        } else {
            return buildRegexp();
        }
    }

    private boolean containsRegexp() {
        // URL regexp is never empty and by default is ".*"
        return !DEFAULT_REGEXP.equals(url.regexp());
    }

    private String buildRegexp() {
        StringBuilder sb = new StringBuilder();
        sb.append("^");
        if (EmptyChecker.empty(url.protocol())) {
            sb.append(".+");
        } else {
            sb.append(url.protocol());
        }
        sb.append("://");
        if (EmptyChecker.empty(url.host())) {
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
