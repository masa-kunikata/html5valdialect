package integration;

import org.junit.Before;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.dom.Document;

import integration.util.XmlUtils;
import net.sourceforge.html5val.Html5ValDialect;

abstract public class IntegrationTestBase {

    private TemplateEngine templateEngine;

    @Before
    public void setUpTemplateEngine() {
    	ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/META-INF/");
        templateResolver.setCacheable(false);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new Html5ValDialect());
    }

    protected Document processTemplate(String templateName, Context context) {
        String html = templateEngine.process(templateName, context);
        return XmlUtils.toDom(html);
    }
}
