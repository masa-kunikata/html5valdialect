package unit.net.sourceforge.html5val.performers;

import javax.validation.constraints.Max;
import net.sourceforge.html5val.ValidationPerformer;
import net.sourceforge.html5val.performers.MaxPerformer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import unit.net.sourceforge.html5val.ValidationPerformerFactoryTest;
import org.junit.Before;
import static org.junit.Assert.*;

public class MaxPerformerTest {

    private final Long maxAllowedValue = 75L;
    private final Mockery context = new JUnit4Mockery();
    private Max maxAnnotation = context.mock(Max.class);
    private ValidationPerformer performer = new MaxPerformer();

    @Before
    public void setUp() {
         context.checking(new Expectations(){{
            allowing(maxAnnotation).value(); will(returnValue(maxAllowedValue));
        }});
     }

    @Test
    public void factoryKnownsPerformer() {
        ValidationPerformerFactoryTest.assertGetPerformer(performer, maxAnnotation);
    }

    @Test
    public void putValidationCodeInto() {
        // Before: <input type="text" />
        Element input = new Element("input");
        input.setAttribute("type", "text");
        performer.putValidationCodeInto(maxAnnotation, input);
        // After: <input type="number" max="75" />
        assertEquals("number", input.getAttributeValue("type"));
        assertEquals(maxAnnotation.value(), Long.parseLong(input.getAttributeValue("max")));
    }
}
