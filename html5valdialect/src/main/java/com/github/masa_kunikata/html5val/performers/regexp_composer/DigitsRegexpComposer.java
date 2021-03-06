package com.github.masa_kunikata.html5val.performers.regexp_composer;

import javax.validation.constraints.Digits;

public class DigitsRegexpComposer implements IRegexpComposer {

    private static final String EMPTY_STRING_REGEXP = "^$";
    private Digits digits;

    private DigitsRegexpComposer(Digits digits) {
        this.digits = digits;
    }

    public static DigitsRegexpComposer forDigits(Digits digits) {
        return new DigitsRegexpComposer(digits);
    }

    @Override
    public String regexp() {
        if (neitherFractionNorInteger()) {
            return EMPTY_STRING_REGEXP;
        } else if (onlyIntegerPart()) {
            return regexForOnlyIntegerPart();
        } else if (onlyFractionPath()) {
            return regexForOnlyFractionPart();
        } else {
            return bothPartsRegex();
        }
    }

    private boolean onlyIntegerPart() {
        return digits.fraction() == 0;
    }

    private boolean onlyFractionPath() {
        return digits.integer() == 0;
    }

    private boolean neitherFractionNorInteger() {
        return digits.fraction() == 0 && digits.integer() == 0;
    }

    private String regexForOnlyIntegerPart() {
        return integerPartRegex() + "\\.?";
    }

    private String integerPartRegex() {
        return "[0-9]{1," + digits.integer() + "}";
    }

    private String regexForOnlyFractionPart() {
        return "\\.[0-9]{1," + digits.fraction() + "}";
    }

    private String bothPartsRegex() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(regexForOnlyIntegerPart());
        sb.append("|");
        sb.append(regexForOnlyFractionPart());
        sb.append("|");
        sb.append(integerPartRegex());
        sb.append(regexForOnlyFractionPart());
        sb.append("){1}");
        return sb.toString();
    }
}
