package unit.net.sourceforge.html5val.performers;

import javax.validation.constraints.Digits;
import net.sourceforge.html5val.ValidationPerformer;
import net.sourceforge.html5val.performers.DigitsPerformer;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import unit.net.sourceforge.html5val.ValidationPerformerFactoryTest;
import static org.junit.Assert.*;
import static unit.util.RegexpMatcher.matchesRegexp;
import static unit.util.RegexpMismatcher.doesNotMatchRegexp;

public class DigitsPerformerTest {

    private ValidationPerformer performer = new DigitsPerformer();

    @Test
    public void factoryKnownsPerformer() {
        ValidationPerformerFactoryTest.assertGetPerformer(performer, new MockDigits());
    }

    @Test
    public void annotationWithIntegerAndFraction() {
        Digits digitsAnnotation = new MockDigits(3, 2);
        // Before: <input type="text" />
        Element input = new Element("input");
        input.setAttribute("type", "text");
        performer.putValidationCodeInto(digitsAnnotation, input);
        // After: <input type="text" pattern="" />
        String pattern = "[0-9]{1,3}(\\.[0-9]{1,2})?";
        assertThat("999.99", matchesRegexp(pattern));
        assertThat("999.9", matchesRegexp(pattern));
        assertThat("999", matchesRegexp(pattern));
        assertThat("99", matchesRegexp(pattern));
        assertThat("9", matchesRegexp(pattern));
        assertThat("a", doesNotMatchRegexp(pattern));
        assertThat("a.b", doesNotMatchRegexp(pattern));
        assertThat("100000", doesNotMatchRegexp(pattern));
        assertThat("1.111", doesNotMatchRegexp(pattern));
        assertThat(".11", doesNotMatchRegexp(pattern));
        assertThat("111.", doesNotMatchRegexp(pattern));
        assertThat(".", doesNotMatchRegexp(pattern));
        assertEquals(pattern, input.getAttributeValue("pattern"));
    }

    @Test
    public void annotationWithInteger() {
        MockDigits digitsAnnotation = new MockDigits();
        digitsAnnotation.setInteger(3);
        // Before: <input type="text" />
        Element input = new Element("input");
        input.setAttribute("type", "text");
        performer.putValidationCodeInto(digitsAnnotation, input);
        // After: <input type="text" pattern="" />
        String pattern = "[0-9]{1,3}";
        assertThat("999", matchesRegexp(pattern));
        assertThat("99", matchesRegexp(pattern));
        assertThat("9", matchesRegexp(pattern));
        assertThat("99999", doesNotMatchRegexp(pattern));
        assertThat("A", doesNotMatchRegexp(pattern));
        assertThat(".9", doesNotMatchRegexp(pattern));
        assertThat("9.", doesNotMatchRegexp(pattern));
        assertThat("9.9", doesNotMatchRegexp(pattern));
        assertThat(".", doesNotMatchRegexp(pattern));
        assertEquals(pattern, input.getAttributeValue("pattern"));
    }

    
    @Test
    public void annotationWithFraction() {
        MockDigits digitsAnnotation = new MockDigits();
        digitsAnnotation.setFraction(2);
            // Before: <input type="text" />
        Element input = new Element("input");
        input.setAttribute("type", "text");
        performer.putValidationCodeInto(digitsAnnotation, input);
        // After: <input type="text" pattern="" />
        String pattern = "[0-9]?\\.[0-9]{1,2}";
        assertThat("9.9", matchesRegexp(pattern));
        assertThat("9.99", matchesRegexp(pattern));
        assertThat(".9", matchesRegexp(pattern));
        assertThat(".99", matchesRegexp(pattern));
        assertThat("A", doesNotMatchRegexp(pattern));
        assertThat("99.9", doesNotMatchRegexp(pattern));
        assertThat("9.999", doesNotMatchRegexp(pattern));
        assertThat("9", doesNotMatchRegexp(pattern));
        assertThat("99", doesNotMatchRegexp(pattern));
        assertThat(".", doesNotMatchRegexp(pattern));
        assertEquals(pattern, input.getAttributeValue("pattern"));
    }
    
}
