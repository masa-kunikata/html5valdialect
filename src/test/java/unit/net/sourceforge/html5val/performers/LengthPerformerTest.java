package unit.net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import net.sourceforge.html5val.performers.LengthPerformer;
import org.hibernate.validator.constraints.Length;
import org.junit.Before;
import unit.net.sourceforge.html5val.*;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import static org.junit.Assert.*;

public class LengthPerformerTest {

    private ValidationPerformer performer = new LengthPerformer();

    private Element input;

    @Before
    public void setUp() {
        // Before: <input type="text" />
        input = new Element("input");
        input.setAttribute("type", "text");
    }

    @Test
    public void factoryKnownsPerformer() {
        Length length = new MockLength();
        ValidationPerformerFactoryTest.assertGetPerformer(performer, length);
    }

    @Test
    public void minAndMax() {
        Length length = new MockLengthBuilder().withMin(2).withMax(5).build();
        performer.putValidationCodeInto(length, input);
        // After: <input type="email" pattern=".{2,5}" />
        assertEquals(".{2,5}", input.getAttributeValue("pattern"));
    }

    @Test
    public void onlyMin() {
        Length length = new MockLengthBuilder().withMin(2).build();
        performer.putValidationCodeInto(length, input);
        // After: <input type="email" pattern=".{2,}" />
        assertEquals(".{2,}", input.getAttributeValue("pattern"));
    }
}
