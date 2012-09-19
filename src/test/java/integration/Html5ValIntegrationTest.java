package integration;

import integration.util.ClasspathResourceResolver;
import java.io.IOException;
import java.io.StringReader;
import net.sourceforge.html5val.Html5ValDialect;
import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.thymeleaf.dom.Document;
import org.thymeleaf.util.DOMUtils;

public class Html5ValIntegrationTest {

    private TemplateEngine templateEngine;
    private Context context;

    @Before
    public void setUpTemplateEngine() {
        TemplateResolver templateResolver = new TemplateResolver();
        templateResolver.setResourceResolver(new ClasspathResourceResolver());
        templateResolver.setPrefix("/META-INF/");
        templateResolver.setCacheable(false);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new Html5ValDialect());
        templateEngine.initialize();
        context = new Context();
    }

    @Test
    public void userFormValidation() throws IOException {
        context.setVariable("userFormBean", new UserFormBean());
        Document html = processTemplate("userForm.html");
        HtmlChecker checker = new HtmlChecker(html);
        checker.elementWithId("username").containsAttributeWithValue("type", "email");
        checker.elementWithId("password").containsAttributeWithValue("pattern", ".{6,}");
    }

    private Document processTemplate(String templateName) {
        String html = templateEngine.process(templateName, context);
        return DOMUtils.getHtml5DOMFor(new StringReader(html));
    }
}
