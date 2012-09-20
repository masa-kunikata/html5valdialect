package net.sourceforge.html5val;

import java.util.HashSet;
import java.util.Set;
import org.thymeleaf.dialect.AbstractXHTMLEnabledDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * Custom extension to Thymeleaf dialect to provide HTML5 validation to forms.
 */
public class Html5ValDialect extends AbstractXHTMLEnabledDialect {

    @Override
    public String getPrefix() {
        return "val";
    }

    @Override
    public boolean isLenient() {
        return false;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        Set<IProcessor> attrProcessors = new HashSet<IProcessor>();
        attrProcessors.add(new ValidateAttrProcessor("validate"));
        return attrProcessors;
    }
}
