package integration;

import java.io.IOException;
import java.io.StringReader;
import org.junit.Test;
import org.thymeleaf.context.Context;
import org.thymeleaf.dom.Document;
import org.thymeleaf.util.DOMUtils;

public class Html5ValIntegrationTest extends IntegrationTestBase {

    private Context context = new Context();

    @Test
    public void userFormValidation() throws IOException {
        context.setVariable("userFormBean", new UserFormBean());
        Document html = processTemplate("userForm.html");
        HtmlChecker checker = new HtmlChecker(html);
        checker.elementWithId("username").containsAttributeWithValue("type", "email");
//        checker.elementWithId("code").containsAttributeWithValue("pattern", ".{5,10}");
    }

    private Document processTemplate(String templateName) {
        String html = getTemplateEngine().process(templateName, context);
        System.out.println(html);
        return DOMUtils.getHtml5DOMFor(new StringReader(html));
    }
}
