package integration;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;

import integration.bean.AllConstraintsBean;
import integration.checker.HtmlChecker;

public class Html5ValIncludeFragmentTest {

    private Context context = new Context();
    private HtmlChecker checker;

    @Before
    public void setUp() {
        context.setVariable("allConstraintsBean", new AllConstraintsBean());
        Document html = IntegrationTestUtils.processTemplate(IntegrationTestUtils.initTemplateEngine(),
                "fragments/mainForm.html", context);
        checker = new HtmlChecker(html);
    }

    @Test
    public void allConstraintsNameAttrForm() throws IOException {
        //normal form
        checker.elementWithId("min10Max200Field").containsAttributeWithValue("type", "number");
        checker.elementWithId("min10Max200Field").containsAttributeWithValue("max", "200");
        checker.elementWithId("min10Max200Field").containsAttributeWithValue("min", "10");

        //th:included fragment
        checker.elementWithId("notNullField").containsAttributeWithValue("type", "text");
        checker.elementWithId("notNullField").containsAttributeWithValue("required", "required");

        checker.elementWithId("patternAlphabet5charsField").containsAttributeWithValue("type", "text");
        checker.elementWithId("patternAlphabet5charsField").containsAttributeWithValue("pattern", "^[a-z]{5}$");

        checker.elementWithId("size5to10Field").containsAttributeWithValue("type", "text");
        checker.elementWithId("size5to10Field").containsAttributeWithValue("pattern", ".{5,10}");
        checker.elementWithId("size5to10Field").containsAttributeWithValue("required", "required");
        checker.elementWithId("size5to10Field").containsAttributeWithValue("maxlength", "10");
    }

}
