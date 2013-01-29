package unit.net.sourceforge.html5val.performers;

import javax.validation.constraints.Digits;
import net.sourceforge.html5val.performers.DigitsRegexpComposer;
import org.junit.Test;

public class DigitsRegexComposerTest {

    @Test
    public void annotationWithPositiveIntegerAndFraction() {
        Digits digitsAnnotation = new MockDigits(3, 2);
        DigitsRegexpComposer composer = DigitsRegexpComposer.forDigits(digitsAnnotation);
        String pattern = composer.regexp();
        ValidationChecker checker = new ValidationChecker(digitsAnnotation, pattern);
        checker.isValid("999.99");
        checker.isValid("999.9");
        checker.isValid("999");
        checker.isValid("99");
        checker.isValid("9");
        checker.isValid("0");
        checker.isValid("111.");
        checker.isValid(".11");
        checker.isNotValid("a");
        checker.isNotValid("a.b");
        checker.isNotValid("100000");
        checker.isNotValid("1.111");
        checker.isNotValid(".");
    }

    @Test
    public void annotationWithFractionZero() {
        Digits digitsAnnotation = new MockDigits(3, 0);
        DigitsRegexpComposer composer = DigitsRegexpComposer.forDigits(digitsAnnotation);
        String pattern = composer.regexp();
        ValidationChecker checker = new ValidationChecker(digitsAnnotation, pattern);
        checker.isValid("0");
        checker.isValid("100");
        checker.isValid("10.");
        checker.isNotValid(".");
        checker.isNotValid("1000");
        checker.isNotValid("10.5");
    }

    @Test
    public void annotationWithIntegerZero() {
        Digits digitsAnnotation = new MockDigits(0, 2);
        DigitsRegexpComposer composer = DigitsRegexpComposer.forDigits(digitsAnnotation);
        String pattern = composer.regexp();
        ValidationChecker checker = new ValidationChecker(digitsAnnotation, pattern);
        checker.isValid(".5");
        checker.isValid(".55");
        checker.isNotValid(".");
        checker.isNotValid("0");
        checker.isNotValid("1.5");
        checker.isNotValid(".555");
    }

    @Test
    public void annotationWithBothZero() {
        Digits digitsAnnotation = new MockDigits(0, 0);
        DigitsRegexpComposer composer = DigitsRegexpComposer.forDigits(digitsAnnotation);
        String pattern = composer.regexp();
        ValidationChecker checker = new ValidationChecker(digitsAnnotation, pattern);
        checker.isNotValid(".");
        checker.isNotValid("0");
        checker.isNotValid("1");
        checker.isNotValid(".5");
        checker.isNotValid("1.5");
    }
}
