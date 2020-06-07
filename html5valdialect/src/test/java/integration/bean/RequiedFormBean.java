package integration.bean;

import javax.validation.constraints.NotEmpty;

public class RequiedFormBean {

    @NotEmpty
    private String requiredString;

    @NotEmpty
    private String requiredSelection;

    @NotEmpty
    private String requiredText;

    public String getRequiredString() {
        return requiredString;
    }

    public void setRequiredString(String requiredString) {
        this.requiredString = requiredString;
    }

    public String getRequiredSelection() {
        return requiredSelection;
    }

    public void setRequiredSelection(String requiredSelection) {
        this.requiredSelection = requiredSelection;
    }

    public String getRequiredText() {
        return requiredText;
    }

    public void setRequiredText(String requiredText) {
        this.requiredText = requiredText;
    }

}
