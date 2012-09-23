package unit.net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import unit.net.sourceforge.html5val.*;
import net.sourceforge.html5val.performers.NotEmptyPerformer;
import org.hibernate.validator.constraints.NotEmpty;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import static org.junit.Assert.*;

public class NotEmptyPerformerTest {

    private final Mockery context = new JUnit4Mockery();
    private NotEmpty notEmptyAnnotation = context.mock(NotEmpty.class);
    private ValidationPerformer performer = new NotEmptyPerformer();

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
