package unit.net.sourceforge.html5val;

import net.sourceforge.html5val.ValidationPerformerFactory;
import net.sourceforge.html5val.performers.EmailPerformer;
import org.hibernate.validator.constraints.Email;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import static org.junit.Assert.*;

// FIXME: only test the "happy path". Test the boundary conditions.
public class ValidationPerformerFactoryTest {

    private final Mockery context = new JUnit4Mockery();

    private ValidationPerformerFactory factory = new ValidationPerformerFactory();

    @Test
    public void getProcessorFor() {
        Email emailAnnotation = context.mock(Email.class);
        assertEquals(EmailPerformer.class, factory.getProcessorFor(emailAnnotation).getClass());
    }
}
