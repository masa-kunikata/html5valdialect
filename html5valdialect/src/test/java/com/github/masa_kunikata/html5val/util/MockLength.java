package com.github.masa_kunikata.html5val.util;

import java.lang.annotation.Annotation;

import javax.validation.Payload;

import org.hibernate.validator.constraints.Length;

import com.github.masa_kunikata.html5val.performers.regexp_composer.LengthRegexpComposer;

@SuppressWarnings("all")
public class MockLength implements Length {

    private int min;
    /** By default max attribute is MAX_INT */
    private int max = LengthRegexpComposer.MAX_BOUNDARY;

    public MockLength() {
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int min() {
        return min;
    }

    public void setMax(int max) {
        this.max = max;
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
