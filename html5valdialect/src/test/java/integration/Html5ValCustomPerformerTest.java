package integration;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Email;
import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.w3c.dom.Document;

import com.msd_sk.thymeleaf.dialects.html5val.Html5ValDialect;
import com.msd_sk.thymeleaf.dialects.html5val.performers.IValidationPerformer;

import integration.bean.AllConstraintsBean;
import integration.checker.HtmlChecker;

public class Html5ValCustomPerformerTest {

    private Context context = new Context();
    private HtmlChecker checker;


    @Before
    public void setUp() {

        final IValidationPerformer customEmailPerformer = new IValidationPerformer(){
            @Override
            public Class<Email> getConstraintClass() {
                return Email.class;
            }

            @Override
            public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                    IProcessableElementTag elementTag) {
                final IModelFactory modelFactory = context.getModelFactory();
                IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "type", "text");
                modifiedTag = modelFactory.setAttribute(modifiedTag, "pattern", "^[a-z0-9_]+@[a-z0-9_.]+$");
                return modifiedTag;
            }
        };

        final Set<IValidationPerformer> performers = new HashSet<>();
        performers.add(customEmailPerformer);
        final Html5ValDialect html5ValDialect = new Html5ValDialect();
        html5ValDialect.setAdditionalPerformers(performers);

        final TemplateEngine templateEngine = IntegrationTestUtils.initTemplateEngine();
        templateEngine.addDialect(html5ValDialect);

        context.setVariable("allConstraintsBean", new AllConstraintsBean());
        final Document html = IntegrationTestUtils.processTemplate(templateEngine, "allConstraintsNameAttrForm.html", context);
        checker = new HtmlChecker(html);

        html5ValDialect.removePerformer(customEmailPerformer);
    }

    @Test
    public void allConstraintsNameAttrForm() throws IOException {
        checker.elementWithId("emailField").containsAttributeWithValue("type", "text");
        checker.elementWithId("emailField").containsAttributeWithValue("pattern", "^[a-z0-9_]+@[a-z0-9_.]+$");
    }

}
