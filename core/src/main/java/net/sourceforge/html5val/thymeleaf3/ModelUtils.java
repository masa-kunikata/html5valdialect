package net.sourceforge.html5val.thymeleaf3;

import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.ITemplateEvent;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Some utility methods for managing IModel instance
 */
public class ModelUtils {

    /**
     * Get the map of elements with the provided name.
     */
    public static Map<Integer, IProcessableElementTag> getElementsByTagName(IModel model, String tagName) {
		final LinkedHashMap<Integer, IProcessableElementTag> ret = new LinkedHashMap<>();
		int n = model.size();
		while (n-- != 0) {
			final ITemplateEvent event = model.get(n);
			if (event instanceof IProcessableElementTag) {
				IProcessableElementTag elementTag = (IProcessableElementTag)event;
				if(tagName.equals(elementTag.getElementCompleteName())) {
					ret.put(n, elementTag);
				}
			}
		}
        return ret;
    }


}
