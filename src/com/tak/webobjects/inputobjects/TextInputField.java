package com.tak.webobjects.inputobjects;

public class TextInputField extends InputField {
    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    private String inputType;
    public TextInputField(String varName, NamedValue defaultValue) {
        super("input", varName, defaultValue);
        this.inputType = "text";
    }
    public TextInputField(String varName, String defaultValue) {
        super("input", varName, NamedValue.toNamedValue(defaultValue));
        this.inputType = "text";
    }
}
