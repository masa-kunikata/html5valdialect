package net.sourceforge.html5val;

import java.util.HashSet;
import java.util.Set;
import org.thymeleaf.dialect.AbstractXHTMLEnabledDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * Custom extension to Thymeleaf dialect to provide HTML5 validation to forms.
 */
public class Html5ValDialect extends AbstractXHTMLEnabledDialect {

    public static final String ATTR_PREFIX = "val";

    /**
     * Add a set of additional ValidationPerformers to ValidateProcessor.
     */
    public void setAdditionalPerformers(Set<ValidationPerformer> additionalPerformers) {
        for (ValidationPerformer performer : additionalPerformers) {
            ValidationPerformerFactory.addCustomPerformer(performer);
        }
    }
    
    public String getPrefix() {
        return ATTR_PREFIX;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        Set<IProcessor> attrProcessors = new HashSet<IProcessor>();
        attrProcessors.add(new ValidateAttrProcessor());
        attrProcessors.add(new ValidatePreviousFormAttrProcessor());
        return attrProcessors;
    }
}
