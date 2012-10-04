package integration;

import java.io.IOException;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.context.Context;
import org.thymeleaf.dom.Document;
import org.thymeleaf.util.DOMUtils;

public class Html5ValIntegrationTest extends IntegrationTestBase {

    private Context context = new Context();
    private HtmlChecker checker;
    
    @Before
    public void setUp() {
        context.setVariable("userFormBean", new UserFormBean());
        checker = new HtmlChecker(processTemplate("userForm.html"));
    }

    @Test
    public void userFormValidation() throws IOException {
        checkPersonalData();
        checkAge();
        checkHighSchoolMark();
        checkPersonalWebPage();
        checkHostingServer();
    }

    private Document processTemplate(String templateName) {
        String html = getTemplateEngine().process(templateName, context);
        return DOMUtils.getHtml5DOMFor(new StringReader(html));
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
        checker.elementWithId("personalWebPage").containsAttributeWithValue("pattern", "^http://[a-zA-Z0-9-.]+.[a-zA-Z]{2,3}(/S*)?$");
    }
    
    private void checkHostingServer() {
        checker.elementWithId("applicationWebPage").containsAttributeWithValue("type", "url");
        checker.elementWithId("applicationWebPage").containsAttributeWithValue("pattern", "^(http://localhost:8080/)");
    }
}
