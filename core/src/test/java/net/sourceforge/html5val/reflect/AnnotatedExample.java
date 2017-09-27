package net.sourceforge.html5val.reflect;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

abstract class AnnotatedGrandParent {

    @NotEmpty
    private String type;
}

class AnnotatedChild {

    @NotNull
    private String postalCode;

    private AnnotatedGrandChild grandChild;
}

class AnnotatedGrandChild {

    @NotEmpty
    private String phone;
}


class AnnotatedParent extends  AnnotatedGrandParent {

    @NotNull
    private String name;

    @NotNull
    @Length(max = 100)
    private int age;
}

class AnnotatedExample extends AnnotatedParent {

    @URL
    private String location;

    @Email
    @NotEmpty
    private String email;

    private AnnotatedChild child;
}

