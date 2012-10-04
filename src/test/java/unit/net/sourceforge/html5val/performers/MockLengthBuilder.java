package unit.net.sourceforge.html5val.performers;

public class MockLengthBuilder {

    private MockLength length = new MockLength();

    public MockLengthBuilder withMin(int min) {
        length.setMin(min);
        return this;
    }

    public MockLengthBuilder withMax(int max) {
        length.setMax(max);
        return this;
    }

    public MockLength build() {
        return length;
    }
}
