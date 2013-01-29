package unit.net.sourceforge.html5val.performers;

import javax.validation.constraints.Min;
import net.sourceforge.html5val.ValidationPerformer;
import net.sourceforge.html5val.performers.MinPerformer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import unit.net.sourceforge.html5val.ValidationPerformerFactoryTest;
import static org.junit.Assert.*;

public class MinPerformerTest {

    private final Long minAllowedValue = 75L;
    private final Mockery context = new JUnit4Mockery();
    private Min minAnnotation = context.mock(Min.class);
    private ValidationPerformer performer = new MinPerformer();

    @Before
    public void setUp() {
        context.checking(new Expectations(){{
            allowing(minAnnotation).value(); will(returnValue(minAllowedValue));
        }});
    }

    @Test
    public void factoryKnownsPerformer() {
        ValidationPerformerFactoryTest.assertGetPerformer(performer, minAnnotation);
    }

    @Test
    public void putValidationCodeInto() {
        // Before: <input type="text" />
        Element input = new Element("input");
        input.setAttribute("type", "text");
        performer.putValidationCodeInto(minAnnotation, input);
        // After: <input type="number" min="75" />
        assertEquals("number", input.getAttributeValue("type"));
        assertEquals(minAnnotation.value(), Long.parseLong(input.getAttributeValue("min")));
    }
}
