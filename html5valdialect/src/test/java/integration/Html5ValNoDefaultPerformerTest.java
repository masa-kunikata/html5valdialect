package integration;

import java.io.IOException;

import org.junit.Test;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;

import integration.bean.DefaultPerformerNotSupportedBean;
import integration.checker.HtmlChecker;

public class Html5ValNoDefaultPerformerTest {

    private Context context = new Context();
    private HtmlChecker checker;

    @Test
    public void testNoDefaultPerformer() throws IOException {
        //Arrange
        context.setVariable("defaultPerformerNotSupportedBean", new DefaultPerformerNotSupportedBean());

        //Act
        Document html = IntegrationTestUtils.processTemplate(IntegrationTestUtils.initDefaultTemplateEngine(),
                "noDefaultPerformer.html", context);

        //Assert
        checker = new HtmlChecker(html);

        checker.elementWithId("decimalMinField").containsAttributeWithValue("type", "text");
        checker.elementWithId("decimalMaxField").containsAttributeWithValue("type", "text");
        checker.elementWithId("assertTrueField").containsAttributeWithValue("type", "text");
        checker.elementWithId("assertFalseField").containsAttributeWithValue("type", "text");
        checker.elementWithId("futureField").containsAttributeWithValue("type", "text");
        checker.elementWithId("pastField").containsAttributeWithValue("type", "text");

    }
}
