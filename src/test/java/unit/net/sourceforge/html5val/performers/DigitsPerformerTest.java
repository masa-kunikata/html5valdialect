package unit.net.sourceforge.html5val.performers;

import javax.validation.constraints.Digits;
import net.sourceforge.html5val.ValidationPerformer;
import net.sourceforge.html5val.performers.DigitsPerformer;
import net.sourceforge.html5val.performers.DigitsRegexpComposer;
import net.sourceforge.html5val.performers.RegexpComposer;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import unit.net.sourceforge.html5val.ValidationPerformerFactoryTest;
import static org.junit.Assert.*;

public class DigitsPerformerTest {

    private ValidationPerformer performer = new DigitsPerformer();

    @Test
    public void factoryKnownsPerformer() {
        ValidationPerformerFactoryTest.assertGetPerformer(performer, new MockDigits());
    }

    @Test
    public void annotationWithPositiveIntegerAndFraction() {
        Digits digitsAnnotation = new MockDigits(3, 2);
        // Before: <input type="text" />
        Element input = new Element("input");
        input.setAttribute("type", "text");
        performer.putValidationCodeInto(digitsAnnotation, input);
        // After: <input type="text" pattern="..." />
        RegexpComposer composer = DigitsRegexpComposer.forDigits(digitsAnnotation);
        String pattern = composer.regexp();
        assertEquals(pattern, input.getAttributeValue("pattern"));
    }
}
