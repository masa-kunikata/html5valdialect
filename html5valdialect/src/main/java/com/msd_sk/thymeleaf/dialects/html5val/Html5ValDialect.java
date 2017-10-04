package com.msd_sk.thymeleaf.dialects.html5val;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import com.msd_sk.thymeleaf.dialects.html5val.performers.IValidationPerformer;
import com.msd_sk.thymeleaf.dialects.html5val.performers.ValidationPerformerFactory;

public class Html5ValDialect extends AbstractProcessorDialect {

    public static final String DIALECT_NAME = "Html5 Val Dialect";
    public static final String DIALECT_PREFIX = "val";

    public Html5ValDialect() {
        super(DIALECT_NAME, DIALECT_PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new ValidateAttributeModelProcessor(dialectPrefix));
        // This will remove the xmlns:val attributes we might add for IDE validation
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }

    /**
     * Add a set of additional ValidationPerformers to ValidateProcessor.
     */
    public void setAdditionalPerformers(Set<IValidationPerformer> additionalPerformers) {
        for (IValidationPerformer performer : additionalPerformers) {
            ValidationPerformerFactory.addCustomPerformer(performer);
        }
    }
    /**
     * Remove an additional ValidationPerformer.
     */
    public void removePerformer(IValidationPerformer performer) {
        ValidationPerformerFactory.removeCustomPerformer(performer);
    }

}