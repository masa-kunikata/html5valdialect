package integration;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

class UserFormBean {

    @Email
    private String username;

    @Size(min = 5, max = 10)
    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
