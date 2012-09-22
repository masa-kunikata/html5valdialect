package unit.net.sourceforge.html5val;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

class AnnotatedBeanExample {

    @Email
    @NotEmpty
    private String email;
}
