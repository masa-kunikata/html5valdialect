package net.sourceforge.html5val.examples;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class UserFormBean {

	@NotEmpty
	@Email
	private String username;

	@Size(min = 5, max = 10)
	private String code;

	@Min(value = 18)
	@Max(value = 100)
	@NotNull
	private Integer age;

	@Range(min = 0, max = 10)
	private Integer highSchoolMark;

	@URL
	private String personalWebPage;

	@Digits(integer = 3, fraction = 2)
	private BigDecimal price;

	@Pattern(regexp = "[0-9]{3}-[0-9]{6}")
	private String phoneNumber;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
