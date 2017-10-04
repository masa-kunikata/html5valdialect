package com.msd_sk.thymeleaf.dialects.html5val.reflect;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.junit.Test;

import com.github.masa_kunikata.html5val.reflect.AnnotationExtractor;

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
        assertThat(annotations.size(), is(1));
        Class<?> notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));
    }

    @Test
    public void parentClassFields() {
        AnnotationExtractor extractor = AnnotationExtractor.forClass(AnnotatedParent.class);
        // Own fields
        List<Annotation> annotations = extractor.getAnnotationsForField("name");
        assertNotNull(annotations);
        assertThat(annotations.size(), is(1));
        Class<?> notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));

        annotations = extractor.getAnnotationsForField("age");
        assertNotNull(annotations);
        assertThat(annotations.size(), is(2));
        notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));
        Class<?> lengthClass = annotations.get(1).getClass();
        assertTrue(Length.class.isAssignableFrom(lengthClass));

        // Parent fields
        annotations = extractor.getAnnotationsForField("type");
        assertNotNull(annotations);
        assertThat(annotations.size(), is(1));
        Class<?> notEmptyClass = annotations.get(0).getClass();
        assertTrue(NotEmpty.class.isAssignableFrom(notEmptyClass));

    }

    @Test
    public void extractAnnotations() {
        AnnotationExtractor extractor = AnnotationExtractor.forClass(AnnotatedExample.class);
        List<Annotation> annotations = extractor.getAnnotationsForField("type");
        assertThat(annotations.size(), is(1));
        Class<?> notEmptyClass = annotations.get(0).getClass();
        assertTrue(NotEmpty.class.isAssignableFrom(notEmptyClass));

        annotations = extractor.getAnnotationsForField("email");
        assertThat(annotations.size(), is(2));
        Class<?> emailClass = annotations.get(0).getClass();
        assertTrue(Email.class.isAssignableFrom(emailClass));
        notEmptyClass = annotations.get(1).getClass();
        assertTrue(NotEmpty.class.isAssignableFrom(notEmptyClass));

        annotations = extractor.getAnnotationsForField("name");
        assertThat(annotations.size(), is(1));
        Class<?> notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));

        annotations = extractor.getAnnotationsForField("age");
        assertThat(annotations.size(), is(2));
        notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));
        Class<?> lengthClass = annotations.get(1).getClass();
        assertTrue(Length.class.isAssignableFrom(lengthClass));

        annotations = extractor.getAnnotationsForField("location");
        assertThat(annotations.size(), is(1));
        Class<?> urlClass = annotations.get(0).getClass();
        assertTrue(URL.class.isAssignableFrom(urlClass));

        annotations = extractor.getAnnotationsForField("child.postalCode");
        assertThat(annotations.size(), is(1));
        notNullClass = annotations.get(0).getClass();
        assertTrue(NotNull.class.isAssignableFrom(notNullClass));

        annotations = extractor.getAnnotationsForField("child.grandChild.phone");
        assertThat(annotations.size(), is(1));
        notEmptyClass = annotations.get(0).getClass();
        assertTrue(NotEmpty.class.isAssignableFrom(notEmptyClass));
    }
}
