package unit.net.sourceforge.html5val.performers;

import javax.validation.constraints.Size;
import net.sourceforge.html5val.ValidationPerformer;
import net.sourceforge.html5val.performers.SizePerformer;
import org.junit.Before;
import unit.net.sourceforge.html5val.*;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import static org.junit.Assert.*;

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
        // After: <input type="email" pattern=".{2,5}" />
        assertEquals(".{2,5}", input.getAttributeValue("pattern"));
    }

    @Test
    public void onlyMin() {
        Size size = new MockSizeBuilder().withMin(2).build();
        performer.putValidationCodeInto(size, input);
        // After: <input type="email" pattern=".{2,}" />
        assertEquals(".{2,}", input.getAttributeValue("pattern"));
    }
}
