package unit.net.sourceforge.html5val.performers;

import javax.validation.constraints.NotNull;
import net.sourceforge.html5val.ValidationPerformer;
import unit.net.sourceforge.html5val.*;
import net.sourceforge.html5val.performers.NotNullPerformer;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import static org.junit.Assert.*;

public class NotNullPerformerTest {

    private final Mockery context = new JUnit4Mockery();
    private NotNull notNullAnnotation = context.mock(NotNull.class);
    private ValidationPerformer performer = new NotNullPerformer();

    @Test
    public void factoryKnownsPerformer() {
        ValidationPerformerFactoryTest.assertGetPerformer(performer, notNullAnnotation);
    }

    @Test
    public void putValidationCodeInto() {
        // Before: <input type="text" />
        Element input = new Element("input");
        input.setAttribute("type", "text");
        performer.putValidationCodeInto(notNullAnnotation, input);
        // After: <input type="text" required="required" />
        assertEquals("required", input.getAttributeValue("required"));
    }
}
