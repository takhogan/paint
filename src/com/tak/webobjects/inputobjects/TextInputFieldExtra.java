package com.tak.webobjects.inputobjects;

public class TextInputFieldExtra extends TextInputField{
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    public TextInputFieldExtra(String varName, NamedValue defaultValue, String style) {
        super(varName, defaultValue);
        this.style = style;
    }

    public TextInputFieldExtra(String varName, String defaultValue, String style) {
        super(varName, defaultValue);
        this.style = style;
    }

    private String style;

}
