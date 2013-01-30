package unit.net.sourceforge.html5val;

import java.lang.annotation.Annotation;
import net.sourceforge.html5val.AnnotationExtractor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;
import static org.junit.Assert.*;

// FIXME: only test the "happy path". Test the boundary conditions.
public class AnnotationExtractorTest {

    @Test
    public void extractAnnotations() {
        AnnotationExtractor extractor = AnnotationExtractor.forClass(AnnotatedBeanExample.class);
        Annotation[] annotations = extractor.getAnnotationsFor("email");
        assertEquals(2, annotations.length);
        Class emailClass = annotations[0].getClass();
        assertTrue(Email.class.isAssignableFrom(emailClass));
        Class notEmptyClass = annotations[1].getClass();
        assertTrue(NotEmpty.class.isAssignableFrom(notEmptyClass));
    }
}
