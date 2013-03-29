package unit.net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import net.sourceforge.html5val.performers.RangePerformer;
import org.hibernate.validator.constraints.Range;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import unit.net.sourceforge.html5val.ValidationPerformerFactoryTest;
import org.junit.Before;
import static org.junit.Assert.*;

public class RangePerformerTest {

    private final Long maxAllowedValue = 1L;
    private final Long minAllowedValue = 100L;
    private final Mockery context = new JUnit4Mockery();
    private Range rangeAnnotation = context.mock(Range.class);
    private ValidationPerformer performer = new RangePerformer();

    @Before
    public void setUp() {
         context.checking(new Expectations(){{
            allowing(rangeAnnotation).min(); will(returnValue(minAllowedValue));
            allowing(rangeAnnotation).max(); will(returnValue(maxAllowedValue));
        }});
     }

    @Test
    public void factoryKnownsPerformer() {
        ValidationPerformerFactoryTest.assertGetPerformer(performer, rangeAnnotation);
    }

    @Test
    public void putValidationCodeInto() {
        // Before: <input type="text" />
        Element input = new Element("input");
        input.setAttribute("type", "text");
        performer.putValidationCodeInto(rangeAnnotation, input);
        // After: <input type="range" min="1" max="10" />
        assertEquals("range", input.getAttributeValue("type"));
        assertEquals(rangeAnnotation.min(), Long.parseLong(input.getAttributeValue("min")));
        assertEquals(rangeAnnotation.max(), Long.parseLong(input.getAttributeValue("max")));
    }
}
