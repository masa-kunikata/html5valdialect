package unit.net.sourceforge.html5val;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;


class AnnotatedChild {

    @NotNull
    private String postalCode;
}

class AnnotatedBeanParent {

    @NotNull
    private String name;

    @NotNull
    @Length(max = 100)
    private int age;
}

class AnnotatedBeanExample extends AnnotatedBeanParent {

    @URL
    private String location;

    @Email
    @NotEmpty
    private String email;

    private AnnotatedChild child;
}

