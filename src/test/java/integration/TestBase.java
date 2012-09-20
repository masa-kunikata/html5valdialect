package integration;

import integration.util.ClasspathResourceResolver;
import net.sourceforge.html5val.Html5ValDialect;
import org.junit.Before;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.TemplateResolver;

abstract public class TestBase {

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

    protected TemplateEngine getTemplateEngine() {
        return templateEngine;
    }
}
