package net.sourceforge.html5val.thymeleaf3;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;


/**
 * Simplifies evaluation of Thymeleaf expressions.
 */
public class ExpressionUtils {
    
    public static Object evaluate(final ITemplateContext context, final String expression) {
		
		final IEngineConfiguration configuration = context.getConfiguration();

        /* Obtain the Thymeleaf Standard Expression parser */
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);

        /* Parse the expression value as a Thymeleaf Standard Expression */
        final IStandardExpression standardExpression = parser.parseExpression(context, expression);

        /* Execute the expression just parsed */
        return standardExpression.execute(context);
    }
}
