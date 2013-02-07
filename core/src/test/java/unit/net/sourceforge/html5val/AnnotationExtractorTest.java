package unit.net.sourceforge.html5val;

import net.sourceforge.html5val.AnnotationExtractor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.junit.Test;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

// FIXME: only test the "happy path". Test the boundary conditions.
public class AnnotationExtractorTest {


    @Test
    public void nullFieldname() {
        AnnotationExtractor extractor = AnnotationExtractor.forClass(AnnotatedBeanExample.class);
        List<Annotation> annotations = extractor.getAnnotationsFor(null);
        assertNotNull(annotations);
        assertTrue(annotations.isEmpty());
    }

    @Test
    public void extractAnnotations() {
        AnnotationExtractor extractor = AnnotationExtractor.forClass(AnnotatedBeanExample.class);
        List<Annotation> annotations = extractor.getAnnotationsFor("email");
        assertEquals(2, annotations.size());
        Class emailClass = annotations.get(0).getClass();
        assertTrue(Email.class.isAssignableFrom(emailClass));
        Class notEmptyClass = annotations.get(1).getClass();
        assertTrue(NotEmpty.class.isAssignableFrom(notEmptyClass));

        annotations = extractor.getAnnotationsFor("name");
        assertEquals(1, annotations.size());
        Class notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));

        annotations = extractor.getAnnotationsFor("age");
        assertEquals(2, annotations.size());
        notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));
        Class lengthClass = annotations.get(1).getClass();
        assertTrue(Length.class.isAssignableFrom(lengthClass));

        annotations = extractor.getAnnotationsFor("location");
        assertEquals(1, annotations.size());
        Class urlClass = annotations.get(0).getClass();
        assertTrue(URL.class.isAssignableFrom(urlClass));

        annotations = extractor.getAnnotationsFor("child.postalCode");
        assertEquals(1, annotations.size());
        notNullClass= annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));
    }

    @Test(expected = IllegalArgumentException.class)
    public void annotationsForInvalidField() {
        AnnotationExtractor extractor = AnnotationExtractor.forClass(AnnotatedBeanExample.class);
        extractor.getAnnotationsFor("phone");
    }
}
