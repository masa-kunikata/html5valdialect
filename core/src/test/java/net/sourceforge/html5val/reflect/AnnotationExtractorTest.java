package net.sourceforge.html5val.reflect;

import net.sourceforge.html5val.reflect.AnnotationExtractor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.junit.Test;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AnnotationExtractorTest {


    @Test
    public void nullField() {
        AnnotationExtractor extractor = AnnotationExtractor.forClass(AnnotatedExample.class);
        List<Annotation> annotations = extractor.getAnnotationsForField(null);
        assertNotNull(annotations);
        assertTrue(annotations.isEmpty());
    }

    @Test
    public void unknownField() {
        AnnotationExtractor extractor = AnnotationExtractor.forClass(AnnotatedExample.class);
        List<Annotation> annotations = extractor.getAnnotationsForField("unknownField");
        assertNotNull(annotations);
        assertTrue(annotations.isEmpty());
    }

    @Test
    public void ownClassField() {
        AnnotationExtractor extractor = AnnotationExtractor.forClass(AnnotatedChild.class);
        List<Annotation> annotations = extractor.getAnnotationsForField("postalCode");
        assertNotNull(annotations);
        assertEquals(1, annotations.size());
        Class notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));
    }

    @Test
    public void parentClassFields() {
        AnnotationExtractor extractor = AnnotationExtractor.forClass(AnnotatedParent.class);
        // Own fields
        List<Annotation> annotations = extractor.getAnnotationsForField("name");
        assertNotNull(annotations);
        assertEquals(1, annotations.size());
        Class notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));

        annotations = extractor.getAnnotationsForField("age");
        assertNotNull(annotations);
        assertEquals(2, annotations.size());
        notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));
        Class lengthClass = annotations.get(1).getClass();
        assertTrue(Length.class.isAssignableFrom(lengthClass));

        // Parent fields
        annotations = extractor.getAnnotationsForField("type");
        assertNotNull(annotations);
        assertEquals(1, annotations.size());
        Class notEmptyClass = annotations.get(0).getClass();
        assertTrue(NotEmpty.class.isAssignableFrom(notEmptyClass));


    }

    @Test
    public void extractAnnotations() {
        AnnotationExtractor extractor = AnnotationExtractor.forClass(AnnotatedExample.class);
        List<Annotation> annotations = extractor.getAnnotationsForField("type");
        assertEquals(1, annotations.size());
        Class notEmptyClass = annotations.get(0).getClass();
        assertTrue(NotEmpty.class.isAssignableFrom(notEmptyClass));

        annotations = extractor.getAnnotationsForField("email");
        assertEquals(2, annotations.size());
        Class emailClass = annotations.get(0).getClass();
        assertTrue(Email.class.isAssignableFrom(emailClass));
        notEmptyClass = annotations.get(1).getClass();
        assertTrue(NotEmpty.class.isAssignableFrom(notEmptyClass));

        annotations = extractor.getAnnotationsForField("name");
        assertEquals(1, annotations.size());
        Class notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));

        annotations = extractor.getAnnotationsForField("age");
        assertEquals(2, annotations.size());
        notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));
        Class lengthClass = annotations.get(1).getClass();
        assertTrue(Length.class.isAssignableFrom(lengthClass));

        annotations = extractor.getAnnotationsForField("location");
        assertEquals(1, annotations.size());
        Class urlClass = annotations.get(0).getClass();
        assertTrue(URL.class.isAssignableFrom(urlClass));

        annotations = extractor.getAnnotationsForField("child.postalCode");
        assertEquals(1, annotations.size());
        notNullClass= annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));

        annotations = extractor.getAnnotationsForField("child.grandChild.phone");
        assertEquals(1, annotations.size());
        notEmptyClass= annotations.get(0).getClass();
        assertTrue(NotEmpty.class.isAssignableFrom(notEmptyClass));
    }
}
