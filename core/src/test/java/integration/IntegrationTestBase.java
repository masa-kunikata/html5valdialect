package integration;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Before;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.msd_sk.thymeleaf.dialects.html5val.Html5ValDialect;

import nu.validator.htmlparser.dom.HtmlDocumentBuilder;

abstract public class IntegrationTestBase {

    private TemplateEngine templateEngine;

    @Before
    public void setUpTemplateEngine() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setCacheable(false);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new Html5ValDialect());
    }

    protected Document processTemplate(String templateName, Context context) {
        String html = templateEngine.process(templateName, context);
        try {
            HtmlDocumentBuilder builder = new HtmlDocumentBuilder();
            StringReader reader = new StringReader(html);
            return builder.parse(new InputSource(reader));
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
