package com.msd_sk.thymeleaf.dialects.html5val.util;

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
