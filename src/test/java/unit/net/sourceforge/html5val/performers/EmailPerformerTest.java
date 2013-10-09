package unit.net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import unit.net.sourceforge.html5val.*;
import net.sourceforge.html5val.performers.EmailPerformer;
import org.hibernate.validator.constraints.Email;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import static org.junit.Assert.*;

public class EmailPerformerTest {

    private final Mockery context = new JUnit4Mockery();
    private Email emailAnnotation = context.mock(Email.class);
    private ValidationPerformer performer = new EmailPerformer();

    @Test
    public void factoryKnownsPerformer() {
        ValidationPerformerFactoryTest.assertGetPerformer(performer, emailAnnotation);
    }

    @Test
    public void putValidationCodeInto() {
        // Before: <input type="text" />
        Element input = new Element("input");
        input.setAttribute("type", "text");
        performer.putValidationCodeInto(emailAnnotation, input);
        // After: <input type="email" />
        assertEquals("email", input.getAttributeValue("type"));
    }
}
