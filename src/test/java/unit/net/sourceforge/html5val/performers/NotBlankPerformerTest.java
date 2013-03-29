package unit.net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import net.sourceforge.html5val.performers.NotBlankPerformer;
import unit.net.sourceforge.html5val.*;
import org.hibernate.validator.constraints.NotBlank;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import static org.junit.Assert.*;

public class NotBlankPerformerTest {

    private final Mockery context = new JUnit4Mockery();

    private NotBlank notEmptyAnnotation = context.mock(NotBlank.class);

    private ValidationPerformer performer = new NotBlankPerformer();

    @Test
    public void factoryKnownsPerformer() {
        ValidationPerformerFactoryTest.assertGetPerformer(performer, notEmptyAnnotation);
    }

    @Test
    public void putValidationCodeInto() {
        // Before: <input type="text" />
        Element input = new Element("input");
        input.setAttribute("type", "text");
        performer.putValidationCodeInto(notEmptyAnnotation, input);
        // After: <input type="text" required="required" />
        assertEquals("required", input.getAttributeValue("required"));
    }
}
