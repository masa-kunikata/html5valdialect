package net.sourceforge.html5val.reflect;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

abstract class AnnotatedGrandParent {

    @NotEmpty
    private String type;
}

class AnnotatedChild {

    @NotNull
    private String postalCode;

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
	private AnnotatedChild child;
}

