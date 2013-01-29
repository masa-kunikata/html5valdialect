package net.sourceforge.html5valdialect.examples;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import net.sourceforge.html5val.performers.URLPerformer;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

public class UserFormBean {

    @Email
    @NotEmpty
    private String username;

    @Size(min = 5, max = 10)
    @NotEmpty
    private String code;

    @Min(value = 18)
    @Max(value = 100)
    @NotNull
    private Integer age;

    @Range(min = 0, max = 10)
    @NotNull
    private Integer highSchoolMark;

    @URL(regexp = URLPerformer.URL_REGEXP)
    @NotEmpty
    private String personalWebPage;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHighSchoolMark() {
        return highSchoolMark;
    }

    public void setHighSchoolMark(Integer highSchoolMark) {
        this.highSchoolMark = highSchoolMark;
    }

    public String getPersonalWebPage() {
        return personalWebPage;
    }

    public void setPersonalWebPage(String personalWebPage) {
        this.personalWebPage = personalWebPage;
    }
}
