package net.sourceforge.html5val.performers;

import javax.validation.constraints.Pattern;
import net.sourceforge.html5val.performers.ValidationPerformer;
import net.sourceforge.html5val.performers.PatternPerformer;
import net.sourceforge.html5val.*;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import static org.junit.Assert.*;
import org.junit.Before;

public class PatternPerformerTest {

    private final Mockery context = new JUnit4Mockery();
    private final String PATTERN_REGEXP = "[0-9]*-[a-z]+[0-9a-z]{3}";
    private Pattern patternAnnotation = context.mock(Pattern.class);
    private ValidationPerformer performer = new PatternPerformer();

    @Before
    public void setUp() {
         context.checking(new Expectations(){{
            allowing(patternAnnotation).regexp(); will(returnValue(PATTERN_REGEXP));
        }});
     }

    @Test
    public void factoryKnownsPerformer() {
        ValidationPerformerFactoryTest.assertGetPerformer(performer, patternAnnotation);
    }

    @Test
    public void incompatibleElementTypeRemainUnchanged() {
        // Before: <input type="submit" />
        Element input = new Element("input");
        input.setAttribute("type", "submit");
        performer.putValidationCodeInto(patternAnnotation, input);
        // After: <input type="submit" />
        assertEquals("submit", input.getAttributeValue("type"));
        assertNull(input.getAttributeValue("pattern"));
    }

    @Test
    public void putValidationCodeInto() {
        // Before: <input type="email" />
        Element input = new Element("input");
        input.setAttribute("type", "email");
        performer.putValidationCodeInto(patternAnnotation, input);
        // After: <input type="email" pattern="{patternRegexp}" />
        assertEquals("email", input.getAttributeValue("type"));
        assertEquals(PATTERN_REGEXP, input.getAttributeValue("pattern"));
    }
}
