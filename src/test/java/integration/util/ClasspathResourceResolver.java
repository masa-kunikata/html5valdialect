package integration.util;

import java.io.InputStream;

import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.util.Validate;

public final class ClasspathResourceResolver implements IResourceResolver {

    public static final String NAME = "CLASSPATH";

    public ClasspathResourceResolver() {
        super();
    }

    public String getName() {
        return NAME;
    }

    public InputStream getResourceAsStream(final TemplateProcessingParameters templateProcessingParameters, final String resourceName) {
        Validate.notNull(resourceName, "Resource name cannot be null");
        return this.getClass().getResourceAsStream(resourceName);
    }
}
