package com.tak.webobjects.inputobjects;

public class InputField extends InputObject {

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public NamedValue getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(NamedValue defaultValue) {
        this.defaultValue = defaultValue;
    }


    public InputField(String objectType, String varName, NamedValue defaultValue) {
        super(objectType);
        this.varName = varName;
        this.defaultValue = defaultValue;
    }

    private String varName;
    private NamedValue defaultValue;


}
