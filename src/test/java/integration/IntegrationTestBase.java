package integration;

import integration.util.ClasspathResourceResolver;
import java.io.StringReader;
import net.sourceforge.html5val.Html5ValDialect;
import org.junit.Before;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.dom.Document;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.thymeleaf.util.DOMUtils;

abstract public class IntegrationTestBase {

    private TemplateEngine templateEngine;

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
    }

    protected Document processTemplate(String templateName, Context context) {
        String html = templateEngine.process(templateName, context);
        return DOMUtils.getHtml5DOMFor(new StringReader(html));
    }
}
