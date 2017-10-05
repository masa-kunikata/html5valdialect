# HTML5 validation dialect for the Thymeleaf 3 template engine.

## Overview

<p>
    This Thymeleaf dialect reads JSR-380 annotations and modifies HTML code introducing
    HTML5 form validation code matching the annotations.
</p>

## Rationale

<p>
    JSR-380 provides server-side validation for Java web applications, but client side validation
    has usually been achieved using Javascript. <br />
    Fortunately, HTML5 has brought new attributes for simple browser validation. <br />
    Although HTML5 validation is not supported by all browsers, nowadays the
    <a href="http://caniuse.com/#feat=form-validation">form validation support</a> is quite good,
    so we prefer favoring users with modern browsers and rely on server validation for users with
    older browsers.
</p>

## Installation

### Gradle + Spring Boot users

#### Add the dependence to the <b>build.gradle</b>


```groovy
ext['thymeleaf.version'] = '3.0.7.RELEASE'
ext['thymeleaf-layout-dialect.version'] = '2.2.2'

repositories {
    maven { url 'https://masa-kunikata.github.io/maven/' }
}
dependencies {
    compile("com.github.masa-kunikata.html5valdialect:html5valdialect:3.0.1-SNAPSHOT")
}
```

<p>
    Note that 3.0.x versions are compatible with Thymeleaf 3.0.x versions.
</p>

#### Add the dialect bean to your Spring configuration

* Spring Boot xml configuration example

```xml
<bean id="html5ValDialect" class="com.github.masa_kunikata.html5val.Html5ValDialect"/>
```

* Spring java configuration example

```java
@Bean
Html5ValDialect html5ValDialect() {
    return new Html5ValDialect();
}
```

## Custom validation rules

<p>
    In order to add a custom validation rule you have to implement the <b>IValidationPerformer</b> interface
    and add it to the configuration:
</p>

* Spring Boot xml configuration example

```xml
<bean id="html5ValDialect" class="com.github.masa_kunikata.html5val.Html5ValDialect">
    <property name="additionalPerformers">
        <set>
            <bean class="fqcn.to.MyCustomPerformer" />
        </set>
    </property>
</bean>
```
    
* Spring java configuration example
    
```java
@Bean
Html5ValDialect html5ValDialect() {

    IValidationPerformer myCustomPerformer = new fqcn.to.MyCustomPerformer();
    final Set<IValidationPerformer> performers = new HashSet<>();
    performers.add(myCustomPerformer);
    
    final Html5ValDialect html5ValDialect = new Html5ValDialect();
    html5ValDialect.setAdditionalPerformers(performers);

    return html5ValDialect;
}
```
    
## Default supported constraints

<table class="doc">
    <thead>
        <tr>
            <th>Constraint</th>
            <th>Usage</th>
            <th>Before</th>
            <th>After</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>javax.validation.constraints.Size</td>
            <td>@Size(min = 5, max = 10)</td>
            <td>&lt;input type="text" name="code" /&gt;</td>
            <td>&lt;input type="text" name="code" pattern=".{5,10}" required="required" /&gt;</td>
        </tr>
        <tr>
            <td>javax.validation.constraints.Min</td>
            <td>@Min(value = 18)</td>
            <td>&lt;input type="text" name="age" /&gt;</td>
            <td>&lt;input type="number" name="age" min="18" /&gt;</td>
        </tr>
        <tr>
            <td>javax.validation.constraints.Max</td>
            <td>@Max(value = 65)</td>
            <td>&lt;input type="text" name="age" /&gt;</td>
            <td>&lt;input type="number" name="age" max="65" /&gt;</td>
        </tr>
        <tr>
            <td>javax.validation.constraints.Digits</td>
            <td>@Digits(integer = 3, fraction = 2)</td>
            <td>&lt;input type="text" name="price" /&gt;</td>
            <td>&lt;input type="text" name="price" pattern="([0-9]{1,3}\.?|\.[0-9]{1,2}|[0-9]{1,3}\.[0-9]{1,2}){1}" /&gt;</td>
        </tr>
        <tr>
            <td>javax.validation.constraints.NotNull</td>
            <td>@NotNull</td>
            <td>&lt;input type="text" name="code" /&gt;</td>
            <td>&lt;input type="text" name="code" required="required" /&gt;</td>
        </tr>
        <tr>
            <td>javax.validation.constraints.NotEmpty</td>
            <td>@NotEmpty</td>
            <td>&lt;input type="text" name="code" /&gt;</td>
            <td>&lt;input type="text" name="code" required="required" /&gt;</td>
        </tr>
        <tr>
            <td>javax.validation.constraints.NotBlank</td>
            <td>@NotBlank</td>
            <td>&lt;input type="text" name="code" /&gt;</td>
            <td>&lt;input type="text" name="code" required="required" /&gt;</td>
        </tr>
        <tr>
            <td>org.hibernate.validator.constraints.Range</td>
            <td>@Range(min = 0, max = 10)</td>
            <td>&lt;input type="text" name="rank" /&gt;</td>
            <td>&lt;input type="range" name="rank" min="0" max="10" /&gt;</td>
        </tr>
        <tr>
            <td>org.hibernate.validator.constraints.Length</td>
            <td>@Length(min = 1, max = 10)</td>
            <td>&lt;input type="text" name="rank" /&gt;</td>
            <td>&lt;input type="text" name="rank" pattern=".{1,10}" required="required" /&gt;</td>
        </tr>
        <tr>
            <td>javax.validation.constraints.Email</td>
            <td>@Email</td>
            <td>&lt;input type="text" name="userEmail" /&gt;</td>
            <td>&lt;input type="email" name="userEmail" /&gt;</td>
        </tr>
        <tr>
            <td>org.hibernate.validator.constraints.URL</td>
            <td>@URL(protocol = "https")</td>
            <td>&lt;input type="text" name="website" /&gt;</td>
            <td>&lt;input type="text" name="website" pattern="^ht<span>tps://</span>.+(:[0-9]+)?(/.*)?" /&gt;</td>
        </tr>
    </tbody>
</table>

## Usage

### Spring users

#### Configure JSR-380 annotations in your form bean

```java
class UserFormBean {

    @Email
    @NotEmpty
    private String username;

    @Size(min = 5, max = 10)
    private String code;

    @Min(value = 18)
    @Max(value = 100)
    @NotNull
    private Integer age;

    @Range(min = 0, max = 10)
    private Integer highSchoolMark;

    @URL(regexp = URLPerformer.URL_REGEXP)
    private String personalWebPage;

    @URL(protocol = "http", host = "localhost", port = 8080)
    private String applicationWebPage;

    ...
}
```


#### Add a instance of the form bean to your model in your controller

```java
    @RequestMapping("/userCreate.html")
    public String userCreate(Model model) {
        model.addAttribute("userFormBean", new UserFormBean());
        return "userCreate.html";
    }
```



#### Use <b>val:validate</b> in your HTML form

```html
    <form action="userSave.do" val:validate="${userFormBean}" method="post">
        <p>
            <label for="username">E-mail</label>
            <input type="text" name="username" id="username" />
        </p>
        <p>
            <label for="code">Code</label>
            <input type="text" name="code" id="code" />
        </p>
        <p>
            <label for="age">Age</label>
            <input type="text" name="age" id="age" />
        </p>
        <p>
            <label for="highSchoolMark">Mark</label>
            <input type="text" name="highSchoolMark" id="highSchoolMark" />
        </p>
        <p>
            <label for="personalWebPage">Personal Web Page</label>
            <input type="text" name="personalWebPage" id="personalWebPage" />
        </p>
        <p>
            <label for="applicationWebPage">Demo application Web Page</label>
            <input type="text" name="applicationWebPage" id="applicationWebPage" />
        </p>
        <input type="submit" />
    </form>
```



#### Use <b>@Valid</b> annotation in your controller

```java
    @RequestMapping(value = "/userSave.do", method = RequestMethod.POST)
    public String userSave(@Valid UserFormBean userForm) {
        userDAO.save(userForm.buildUser());
        return "redirect:/userList.html";
    }
```

<p>
    In order to make the example simpler, <b>th:field</b> is not used, but you
    usually would combine <b>val:validate</b> with <b>th:object</b> and <b>th:errors</b>.
</p>


## Quick start the example application

1. Install JDK and Gradle.
2. Download source code
3. At the command prompt, execute `cd html5valdialect-example-webapp` and make it current directory.
4. Run `gradle bootRun`
3. Open [http://localhost:8080/](http://localhost:8080/)


## License

This software is licensed under the [Apache License 2.0]
(http://www.apache.org/licenses/LICENSE-2.0.html).


