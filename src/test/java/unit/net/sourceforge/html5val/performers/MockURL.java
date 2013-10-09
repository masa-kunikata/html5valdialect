package unit.net.sourceforge.html5val.performers;

import java.lang.annotation.Annotation;
import javax.validation.Payload;
import javax.validation.constraints.Pattern.Flag;
import net.sourceforge.html5val.performers.URLRegexpComposer;
import org.hibernate.validator.constraints.URL;

public class MockURL implements URL {

    private String protocol;
    private String host;
    private int port = URLRegexpComposer.EMPTY_PORT;
    private String regexp = URLRegexpComposer.DEFAULT_REGEXP;

    public String message() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public Class<?>[] groups() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public Class<? extends Payload>[] payload() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public String protocol() {
        return protocol;
    }

    public String host() {
        return host;
    }

    public int port() {
        return port;
    }

    public String regexp() {
        return regexp;
    }

    public Flag[] flags() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public Class<? extends Annotation> annotationType() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }
}
