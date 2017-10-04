package com.msd_sk.thymeleaf.dialects.html5val.util;

import java.lang.annotation.Annotation;

import javax.validation.Payload;
import javax.validation.constraints.Digits;

@SuppressWarnings("all")
public class MockDigits implements Digits {

    private int integer;
    private int fraction;

    public MockDigits() {
    }

    public MockDigits(int integer, int fraction) {
        this.integer = integer;
        this.fraction = fraction;
    }

    public int fraction() {
        return fraction;
    }

    public int integer() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public void setFraction(int fraction) {
        this.fraction = fraction;
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