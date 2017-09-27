package net.sourceforge.html5val.performers;

import net.sourceforge.html5val.performers.ValidationPerformer;
import net.sourceforge.html5val.performers.SizePerformer;
import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import net.sourceforge.html5val.ValidationPerformerFactoryTest;

import javax.validation.constraints.Size;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SizePerformerTest {

    private ValidationPerformer performer = new SizePerformer();
    private Element input;

    @Before
    public void setUp() {
        // Before: <input type="text" />
        input = new Element("input");
        input.setAttribute("type", "text");
    }

    @Test
    public void factoryKnownsPerformer() {
        Size size = new MockSize();
        ValidationPerformerFactoryTest.assertGetPerformer(performer, size);
    }

    @Test
    public void minAndMax() {
        Size size = new MockSizeBuilder().withMin(2).withMax(5).build();
        performer.putValidationCodeInto(size, input);
        // After: <input type="text" pattern=".{2,5}" maxlength="5" required="required" />
        assertEquals(".{2,5}", input.getAttributeValue("pattern"));
	    assertEquals("5", input.getAttributeValue("maxlength"));
        assertEquals("required", input.getAttributeValue("required"));
    }

    @Test
    public void onlyMin() {
        Size size = new MockSizeBuilder().withMin(2).build();
        performer.putValidationCodeInto(size, input);
        // After: <input type="text" pattern=".{2,}" required="required" />
        assertEquals(".{2,}", input.getAttributeValue("pattern"));
        assertNull(input.getAttributeValue("maxlength"));
        assertEquals("required", input.getAttributeValue("required"));
    }

    @Test
    public void notMin() {
        Size size = new MockSizeBuilder().withMin(0).withMax(5).build();
        performer.putValidationCodeInto(size, input);
        // After: <input type="text" pattern=".{0,5}" maxlength="5"/>
        assertEquals(".{0,5}", input.getAttributeValue("pattern"));
        assertEquals("5", input.getAttributeValue("maxlength"));
        assertNull(input.getAttributeValue("required"));
    }
}
