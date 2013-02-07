package unit.net.sourceforge.html5val.performers;

import net.sourceforge.html5val.ValidationPerformer;
import net.sourceforge.html5val.performers.URLPerformer;
import unit.net.sourceforge.html5val.*;
import org.hibernate.validator.constraints.URL;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.thymeleaf.dom.Element;
import static org.junit.Assert.*;
import org.junit.Before;

public class URLPerformerTest {

    private final Mockery context = new JUnit4Mockery();
    private final String URL_REGEXP = "^http://[a-zA-Z0-9-.]+.[a-zA-Z]{2,3}(/S*)?$";
    private URL urlAnnotation = context.mock(URL.class);
    private ValidationPerformer performer = new URLPerformer();

    @Before
    public void setUp() {
        context.checking(new Expectations(){{
            allowing(urlAnnotation).regexp(); will(returnValue(URL_REGEXP));
        }});
    }

    @Test
    public void factoryKnownsPerformer() {
        ValidationPerformerFactoryTest.assertGetPerformer(performer, urlAnnotation);
    }

    @Test
    public void putValidationCodeInto() {
        // Before: <input type="url" />
        Element input = new Element("input");
        input.setAttribute("type", "url");
        performer.putValidationCodeInto(urlAnnotation, input);
        // After: <input type="url" pattern="{patternRegexp}" required="required" />
        assertEquals("url", input.getAttributeValue("type"));
        assertEquals(URL_REGEXP, input.getAttributeValue("pattern"));
    }
}
