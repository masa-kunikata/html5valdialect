package net.sourceforge.html5val.performers;

public class LengthRegexpComposer {

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
