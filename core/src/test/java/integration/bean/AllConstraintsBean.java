package integration.bean;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class AllConstraintsBean {

    @Digits(integer = 3, fraction = 2)
    private Long digitsInt3Frac2Field;

    @Max(value = 100)
    private Integer max100Field;

    @Min(value = 18)
    private Integer min18Field;

    @Min(value = 10)
    @Max(value = 200)
    private Integer min10Max200Field;

    @NotNull
    private String notNullField;

    @Pattern(regexp = "^[a-z]{5}$")
    private String patternAlphabet5charsField;

    @Size(min = 5, max = 10)
    private String size5to10Field;

    @Email
    private String emailField;

    @Length(min = 6, max = 11)
    private String length6to11Field;

    @NotBlank
    private String notBlankField;

    @NotEmpty
    private String notEmptyField;

    @Range(min = 0, max = 10)
    private Integer rangeMin0Max10Field;

    @URL
    private String urlField;

    @URL(protocol = "http", host = "localhost", port = 8080)
    private String urlHttpLocalhost8080Field;
}
