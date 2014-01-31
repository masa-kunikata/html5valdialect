package unit.net.sourceforge.html5val;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import net.sourceforge.html5val.FormElementFinder;
import org.junit.Test;
import org.thymeleaf.dom.DOMSelector;
import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.util.DOMUtils;
import static org.junit.Assert.*;

// FIXME: only test the "happy path". Test the boundary conditions.
public class FormElementFinderTest {

    @Test
    public void findFormElements() {
        Element form = readForm();
        Set<Element> fields = FormElementFinder.findFormElements(form);
        assertNotNull(fields);
        assertEquals(8, fields.size());
        assertFieldIsInSet("username", fields);
        assertFieldIsInSet("phone", fields);
        assertFieldIsInSet("relationship", fields);
        assertFieldIsInSet("enabled", fields);
        assertFieldIsInSet("city", fields);
        assertFieldIsInSet("cv", fields);
    }

    private Element readForm() {
        InputStream stream = this.getClass().getResourceAsStream("/META-INF/formExample.html");
        Reader reader = new InputStreamReader(stream);
        Document html = DOMUtils.getHtml5DOMFor(reader);
        DOMSelector formSelector = new DOMSelector("//form");
        List<Node> formList = formSelector.select(html);
        return (Element) formList.get(0);
    }

    private void assertFieldIsInSet(String name, Set<Element> fields) {
        for (Element field : fields) {
            if (name.equals(field.getAttributeValue("name"))) {
                return; // Everything OK
            }
        }
        fail("Field set does not contain field with name " + name);
    }
}
