package integration;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

class UserFormBean {

    @Email
    private String username;

    @Size(min = 6)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
