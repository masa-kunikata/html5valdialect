package integration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

class UserFormBean {

    @Email
    private String username;

    @Size(min = 5, max = 10)
    private String code;

    @Min(value = 18)
    @Max(value = 100)
    private Integer age;

    @Range(min = 0, max = 10)
    private Integer highSchoolMark;

    @URL
    private String personalWebPage;

    @URL(protocol = "http", host = "localhost", port = 8080)
    private String applicationWebPage;

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

    public String getApplicationWebPage() {
        return applicationWebPage;
    }

    public void setApplicationWebPage(String applicationWebPage) {
        this.applicationWebPage = applicationWebPage;
    }

}
