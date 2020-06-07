package integration;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;

import integration.bean.RequiedFormBean;
import integration.checker.HtmlChecker;

public class Html5ValRequiredFormIntegrationTest {

    private Context context = new Context();
    private HtmlChecker checker;

    @Before
    public void setUp() {
        context.setVariable("requiedFormBean", new RequiedFormBean());
        Document html = IntegrationTestUtils.processTemplate(IntegrationTestUtils.initDefaultTemplateEngine(),
                "requiredForm.html",
                context);
        checker = new HtmlChecker(html);
    }

    @Test
    public void requiedFormValidation() throws IOException {
        checker.elementWithId("requiredString").containsAttributeWithValue("required", "required");
        checker.elementWithId("requiredSelection").containsAttributeWithValue("required", "required");
        checker.elementWithId("requiredText").containsAttributeWithValue("required", "required");
    }
}
