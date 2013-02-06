package net.sourceforge.html5val;

import org.thymeleaf.dom.Element;

/**
 * Thymeleaf processors run before the evaluation of their childen.
 * This class helps to acomplish that goal evaluating a processor after a provided element.
 */
public class DelayedProcessorInvoker {

    static void invokeProcessorAfterElement(Element element, String attributeProcessorName, String attributeProcessorValue) {
        Element invoker = new Element("div");
        invoker.setAttribute(attributeProcessorName, attributeProcessorValue);
        invoker.setProcessable(true);
        element.getParent().insertAfter(element, invoker);
    }
}
