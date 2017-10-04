package integration;

import java.io.IOException;

import org.junit.Test;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;

import integration.bean.AllConstraintsBean;
import integration.checker.HtmlChecker;

public class Html5ValAllConstraintsTest {

    private Context context = new Context();
    private HtmlChecker checker;

    @Test
    public void allConstraintsNameAttrForm() throws IOException {
        //Arrange
        context.setVariable("allConstraintsBean", new AllConstraintsBean());

        //Act
        Document html = IntegrationTestUtils.processTemplate(IntegrationTestUtils.initDefaultTemplateEngine(),
                "allConstraintsNameAttrForm.html", context);

        //Assert
        checker = new HtmlChecker(html);

        checker.elementWithId("digitsInt3Frac2Field").containsAttributeWithValue("type", "text");
        checker.elementWithId("digitsInt3Frac2Field").containsAttributeWithValue("pattern",
                "([0-9]{1,3}\\.?|\\.[0-9]{1,2}|[0-9]{1,3}\\.[0-9]{1,2}){1}");

        checker.elementWithId("max100Field").containsAttributeWithValue("type", "number");
        checker.elementWithId("max100Field").containsAttributeWithValue("max", "100");

        checker.elementWithId("min18Field").containsAttributeWithValue("type", "number");
        checker.elementWithId("min18Field").containsAttributeWithValue("min", "18");

        checker.elementWithId("min10Max200Field").containsAttributeWithValue("type", "number");
        checker.elementWithId("min10Max200Field").containsAttributeWithValue("max", "200");
        checker.elementWithId("min10Max200Field").containsAttributeWithValue("min", "10");

        checker.elementWithId("notNullField").containsAttributeWithValue("type", "text");
        checker.elementWithId("notNullField").containsAttributeWithValue("required", "required");

        checker.elementWithId("patternAlphabet5charsField").containsAttributeWithValue("type", "text");
        checker.elementWithId("patternAlphabet5charsField").containsAttributeWithValue("pattern", "^[a-z]{5}$");

        checker.elementWithId("size5to10Field").containsAttributeWithValue("type", "text");
        checker.elementWithId("size5to10Field").containsAttributeWithValue("pattern", ".{5,10}");
        checker.elementWithId("size5to10Field").containsAttributeWithValue("required", "required");
        checker.elementWithId("size5to10Field").containsAttributeWithValue("maxlength", "10");

        checker.elementWithId("emailField").containsAttributeWithValue("type", "email");

        checker.elementWithId("length6to11Field").containsAttributeWithValue("type", "text");
        checker.elementWithId("length6to11Field").containsAttributeWithValue("pattern", ".{6,11}");
        checker.elementWithId("length6to11Field").containsAttributeWithValue("required", "required");
        checker.elementWithId("length6to11Field").containsAttributeWithValue("maxlength", "11");

        checker.elementWithId("notBlankField").containsAttributeWithValue("type", "text");
        checker.elementWithId("notBlankField").containsAttributeWithValue("required", "required");

        checker.elementWithId("notEmptyField").containsAttributeWithValue("type", "text");
        checker.elementWithId("notEmptyField").containsAttributeWithValue("required", "required");

        checker.elementWithId("rangeMin0Max10Field").containsAttributeWithValue("type", "range");
        checker.elementWithId("rangeMin0Max10Field").containsAttributeWithValue("min", "0");
        checker.elementWithId("rangeMin0Max10Field").containsAttributeWithValue("max", "10");

        checker.elementWithId("urlField").containsAttributeWithValue("type", "url");
        checker.elementWithId("urlField").containsAttributeWithValue("pattern", "^.+://.+(:[0-9]+)?(/.*)?");

        checker.elementWithId("urlHttpLocalhost8080Field").containsAttributeWithValue("type", "url");
        checker.elementWithId("urlHttpLocalhost8080Field").containsAttributeWithValue("pattern",
                "^http://localhost:8080(/.*)?");
    }

    @Test
    public void allConstraintsThFieldAttrForm() throws IOException {
        //Arrange
        context.setVariable("allConstraintsBean", new AllConstraintsBean());

        //Act
        Document html = IntegrationTestUtils.processTemplate(IntegrationTestUtils.initDefaultTemplateEngine(),
                "allConstraintsThFieldAttrForm.html", context);

        //Assert
        checker = new HtmlChecker(html);

        checker.elementWithId("digitsInt3Frac2Field").containsAttributeWithValue("type", "text");
        checker.elementWithId("digitsInt3Frac2Field").containsAttributeWithValue("pattern",
                "([0-9]{1,3}\\.?|\\.[0-9]{1,2}|[0-9]{1,3}\\.[0-9]{1,2}){1}");

        checker.elementWithId("max100Field").containsAttributeWithValue("type", "number");
        checker.elementWithId("max100Field").containsAttributeWithValue("max", "100");

        checker.elementWithId("min18Field").containsAttributeWithValue("type", "number");
        checker.elementWithId("min18Field").containsAttributeWithValue("min", "18");

        checker.elementWithId("min10Max200Field").containsAttributeWithValue("type", "number");
        checker.elementWithId("min10Max200Field").containsAttributeWithValue("max", "200");
        checker.elementWithId("min10Max200Field").containsAttributeWithValue("min", "10");

        checker.elementWithId("notNullField").containsAttributeWithValue("type", "text");
        checker.elementWithId("notNullField").containsAttributeWithValue("required", "required");

        checker.elementWithId("patternAlphabet5charsField").containsAttributeWithValue("type", "text");
        checker.elementWithId("patternAlphabet5charsField").containsAttributeWithValue("pattern", "^[a-z]{5}$");

        checker.elementWithId("size5to10Field").containsAttributeWithValue("type", "text");
        checker.elementWithId("size5to10Field").containsAttributeWithValue("pattern", ".{5,10}");
        checker.elementWithId("size5to10Field").containsAttributeWithValue("required", "required");
        checker.elementWithId("size5to10Field").containsAttributeWithValue("maxlength", "10");

        checker.elementWithId("emailField").containsAttributeWithValue("type", "email");

        checker.elementWithId("length6to11Field").containsAttributeWithValue("type", "text");
        checker.elementWithId("length6to11Field").containsAttributeWithValue("pattern", ".{6,11}");
        checker.elementWithId("length6to11Field").containsAttributeWithValue("required", "required");
        checker.elementWithId("length6to11Field").containsAttributeWithValue("maxlength", "11");

        checker.elementWithId("notBlankField").containsAttributeWithValue("type", "text");
        checker.elementWithId("notBlankField").containsAttributeWithValue("required", "required");

        checker.elementWithId("notEmptyField").containsAttributeWithValue("type", "text");
        checker.elementWithId("notEmptyField").containsAttributeWithValue("required", "required");

        checker.elementWithId("rangeMin0Max10Field").containsAttributeWithValue("type", "range");
        checker.elementWithId("rangeMin0Max10Field").containsAttributeWithValue("min", "0");
        checker.elementWithId("rangeMin0Max10Field").containsAttributeWithValue("max", "10");

        checker.elementWithId("urlField").containsAttributeWithValue("type", "url");
        checker.elementWithId("urlField").containsAttributeWithValue("pattern", "^.+://.+(:[0-9]+)?(/.*)?");

        checker.elementWithId("urlHttpLocalhost8080Field").containsAttributeWithValue("type", "url");
        checker.elementWithId("urlHttpLocalhost8080Field").containsAttributeWithValue("pattern",
                "^http://localhost:8080(/.*)?");
    }
}
