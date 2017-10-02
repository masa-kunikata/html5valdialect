package net.sourceforge.html5val.performers.regexp_composer;

import static org.junit.Assert.*;

import org.junit.Test;

public class LengthRegexComposerTest {

    @Test
    public void minAndMax() {
        int min = 1;
        int max = 154;
        LengthRegexpComposer composer = LengthRegexpComposer.forMinAndMax(min, max);
        String regexp = composer.regexp();
        String regexpExpected = ".{1,154}";
        assertEquals(regexpExpected, regexp);
    }

     @Test
    public void onlyMin() {
        int min = 50;
        int max = LengthRegexpComposer.MAX_BOUNDARY;
        LengthRegexpComposer composer = LengthRegexpComposer.forMinAndMax(min, max);
        String regexp = composer.regexp();
        String regexpExpected = ".{50,}";
        assertEquals(regexpExpected, regexp);
    }
}
