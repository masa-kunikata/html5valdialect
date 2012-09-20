package integration;

import java.io.IOException;
import java.io.StringReader;
import org.junit.Test;
import org.thymeleaf.context.Context;
import org.thymeleaf.dom.Document;
import org.thymeleaf.util.DOMUtils;

public class Html5ValIntegrationTest extends TestBase {

    private Context context = new Context();
    
    @Test
    public void userFormValidation() throws IOException {
        context.setVariable("userFormBean", new UserFormBean());
        Document html = processTemplate("userForm.html");
        HtmlChecker checker = new HtmlChecker(html);
        checker.elementWithId("username").containsAttributeWithValue("type", "email");
        checker.elementWithId("password").containsAttributeWithValue("pattern", ".{6,}");
    }

    private Document processTemplate(String templateName) {
        String html = getTemplateEngine().process(templateName, context);
        return DOMUtils.getHtml5DOMFor(new StringReader(html));
    }
}
