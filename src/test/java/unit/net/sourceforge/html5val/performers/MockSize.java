package unit.net.sourceforge.html5val.performers;

import java.lang.annotation.Annotation;
import javax.validation.Payload;
import javax.validation.constraints.Size;

public class MockSize implements Size {

    private int min;
    private int max;

    public MockSize() {
    }

    public MockSize(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }

    public String message() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Class<?>[] groups() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Class<? extends Payload>[] payload() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Class<? extends Annotation> annotationType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
