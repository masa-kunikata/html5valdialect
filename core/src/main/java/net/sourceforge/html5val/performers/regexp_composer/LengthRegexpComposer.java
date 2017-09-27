package net.sourceforge.html5val.performers.regexp_composer;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class LengthRegexpComposer implements IRegexpComposer {

    public static final int MAX_BOUNDARY = Integer.MAX_VALUE;

    private int min;

    private int max;

    private LengthRegexpComposer(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static LengthRegexpComposer forMinAndMax(int min, int max) {
        return new LengthRegexpComposer(min, max);
    }

    public static LengthRegexpComposer forSize(Size size) {
        return new LengthRegexpComposer(size.min(), size.max());
    }

    public static LengthRegexpComposer forLength(Length length) {
        return new LengthRegexpComposer(length.min(), length.max());
    }

	@Override
    public String regexp() {
        StringBuilder sb = new StringBuilder();
        sb.append(".{");
        sb.append(min).append(",");
        if (max < MAX_BOUNDARY) {
            sb.append(max);
        }
        sb.append("}");
        return sb.toString();
    }
}
