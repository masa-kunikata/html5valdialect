package unit.net.sourceforge.html5val.constraints;

import javax.validation.ConstraintValidatorContext;
import net.sourceforge.html5val.constraints.Equals;
import net.sourceforge.html5val.constraints.EqualsValidator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EqualsValidatorTest {

    private final Mockery context = new JUnit4Mockery();
    private final String STRING_PROPERTY1 = "property1";
    private final String STRING_PROPERTY2 = "property2";
    private final String INT_PROPERTY = "intProperty";

    private EqualsValidator validator;
    private ConstraintValidatorContext validatorContext;
    private Equals annotation;

    @Before
    public void setUp() {
        validator = new EqualsValidator();
        annotation = context.mock(Equals.class);
        context.checking(new Expectations() {{
            allowing(annotation).property1(); will(returnValue(STRING_PROPERTY1));
            allowing(annotation).property2(); will(returnValue(STRING_PROPERTY2));
        }});
    }

    @Test
    public void equals() {
        validator.initialize(annotation);
        Object testObject = MockObject.build("aaa", "aaa");
        assertTrue(validator.isValid(testObject, validatorContext));
    }

    @Test
    public void notEquals() {
        validator.initialize(annotation);
        Object testObject = MockObject.build("aaa", "bbb");
        assertFalse(validator.isValid(testObject, validatorContext));
    }

    @Test
    public void bothPropertiesNull() {
        validator.initialize(annotation);
        Object testObject = MockObject.build(null, null);
        assertTrue(validator.isValid(testObject, validatorContext));
    }

    @Test
    public void firstPropertyNull() {
        validator.initialize(annotation);
        Object testObject = MockObject.build(null, "bbb");
        assertFalse(validator.isValid(testObject, validatorContext));
    }

    @Test
    public void secondPropertyNull() {
        validator.initialize(annotation);
        Object testObject = MockObject.build("aaa", null);
        assertFalse(validator.isValid(testObject, validatorContext));
    }

    @Test(expected = IllegalArgumentException.class)
    public void differentClasses() {
        final Equals differentTypeAnnotation = context.mock(Equals.class, "diff");
        context.checking(new Expectations() {{
            allowing(differentTypeAnnotation).property1(); will(returnValue(STRING_PROPERTY1));
            allowing(differentTypeAnnotation).property2(); will(returnValue(INT_PROPERTY));
        }});
        validator.initialize(differentTypeAnnotation);
        Object testObject = MockObject.buildWithIntProperty("aaa", 123);
        validator.isValid(testObject, validatorContext);
    }
}
