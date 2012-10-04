package unit.net.sourceforge.html5val.performers;

public class MockSizeBuilder {

    private MockSize size = new MockSize();

    public MockSizeBuilder withMin(int min) {
        size.setMin(min);
        return this;
    }

    public MockSizeBuilder withMax(int max) {
        size.setMax(max);
        return this;
    }

    public MockSize build() {
        return size;
    }
}
