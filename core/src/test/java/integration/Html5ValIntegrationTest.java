package integration;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.context.Context;
import org.thymeleaf.dom.Document;

public class Html5ValIntegrationTest extends IntegrationTestBase {

    private static final String URL_REGEXP = "^.+://.+(:[0-9]+)?(/.*)?";
    private Context context = new Context();
    private HtmlChecker checker;

    @Before
    public void setUp() {
        context.setVariable("userFormBean", new UserFormBean());
        Document html = processTemplate("userForm.html", context);
        checker = new HtmlChecker(html);
    }

    @Test
    public void userFormValidation() throws IOException {
        checkPersonalData();
        checkAge();
        checkHighSchoolMark();
        checkPersonalWebPage();
        checkHostingServer();
    }

    private void checkPersonalData() {
        checker.elementWithId("username").containsAttributeWithValue("type", "email");
        checker.elementWithId("code").containsAttributeWithValue("pattern", ".{5,10}");
    }

    private void checkAge() {
        checker.elementWithId("age").containsAttributeWithValue("type", "number");
        checker.elementWithId("age").containsAttributeWithValue("min", "18");
        checker.elementWithId("age").containsAttributeWithValue("max", "100");
    }

    private void checkHighSchoolMark() {
        checker.elementWithId("highSchoolMark").containsAttributeWithValue("type", "range");
        checker.elementWithId("highSchoolMark").containsAttributeWithValue("min", "0");
        checker.elementWithId("highSchoolMark").containsAttributeWithValue("max", "10");
    }

    private void checkPersonalWebPage() {
        checker.elementWithId("personalWebPage").containsAttributeWithValue("type", "url");
        checker.elementWithId("personalWebPage").containsAttributeWithValue("pattern", URL_REGEXP);
    }

    private void checkHostingServer() {
        checker.elementWithId("applicationWebPage").containsAttributeWithValue("type", "url");
        checker.elementWithId("applicationWebPage").containsAttributeWithValue("pattern", "^http://localhost:8080(/.*)?");
    }
}
