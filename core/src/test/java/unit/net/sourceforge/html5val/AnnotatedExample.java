package unit.net.sourceforge.html5val;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

abstract class AnnotatedGrandParent {

    @NotEmpty
    private String type;

    public String getType() {
        return type;
    }
}

class AnnotatedChild {

    @NotNull
    private String postalCode;

    public String getPostalCode() {
        return postalCode;
    }
}

class AnnotatedParent extends  AnnotatedGrandParent {

    @NotNull
    private String name;

    @NotNull
    @Length(max = 100)
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

class AnnotatedExample extends AnnotatedParent {

    @URL
    private String location;

    @Email
    @NotEmpty
    private String email;

    private AnnotatedChild child;

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public AnnotatedChild getChild() {
        return child;
    }
}

