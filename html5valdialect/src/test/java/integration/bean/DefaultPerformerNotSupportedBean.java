package integration.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

public class DefaultPerformerNotSupportedBean {

    @DecimalMin(value = "10")
    private BigDecimal decimalMinField;

    @DecimalMax(value = "100")
    private BigDecimal decimalMaxField;

    @AssertTrue
    private boolean assertTrueField;

    @AssertFalse
    private boolean assertFalseField;

    @Future
    private LocalDateTime futureField;

    @Past
    private LocalDateTime pastField;

}
