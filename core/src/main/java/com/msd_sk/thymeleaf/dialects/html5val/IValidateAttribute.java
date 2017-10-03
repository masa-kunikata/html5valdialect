package com.msd_sk.thymeleaf.dialects.html5val;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;

/** */
interface IValidateAttribute {
    void execute(final ITemplateContext context, final IModel model, final String attributeValue);

}