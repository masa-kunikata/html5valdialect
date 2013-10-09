package unit.net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import net.sourceforge.html5val.ValidationPerformer;
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

    @Test
    public void getPerformerFor() {
        Email emailAnnotation = context.mock(Email.class);
        assertGetPerformer(EmailPerformer.class, emailAnnotation);
    }

    private static void assertGetPerformer(Class expectedPerformerClass, Annotation annotation) {
        ValidationPerformer performer = ValidationPerformerFactory.getPerformerFor(annotation);
        assertEquals(expectedPerformerClass, performer.getClass());
    }

    /** Public method to be used in every ValidationPerformer test. */
    public static void assertGetPerformer(ValidationPerformer expectedPerformer, Annotation annotation) {
        assertGetPerformer(expectedPerformer.getClass(), annotation);
    }
}
