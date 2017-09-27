package net.sourceforge.html5val.thymeleaf3;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

/**
 * Simplifies evaluation of Thymeleaf expressions.
 */
public class ExpressionUtil {
    
    public static Object evaluate(Arguments arguments, String expression) {
        Configuration configuration = arguments.getConfiguration();
        IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
        IStandardExpression standardExpression = expressionParser.parseExpression(configuration, arguments, expression);
        return standardExpression.execute(configuration, arguments);
    }
}
