package com.tak.webobjects.inputobjects;

import java.util.ArrayList;

public class SelectContainerFExtra extends SelectContainerExtra{
    public SelectContainerFExtra(String varName, NamedValue defaultValue, ArrayList<NamedValue> options, String style, String onChange) {
        super(varName, defaultValue, options, style);
        this.onChange = onChange;
    }

    public SelectContainerFExtra(String varName, String defaultValue, ArrayList<String> options, String style, String onChange) {
        super(varName, defaultValue, options, style);
        System.out.println("onChange = " + onChange);
        this.onChange = onChange;
    }

    public String getOnChange() {
        return onChange;
    }

    public void setOnChange(String onChange) {
        this.onChange = onChange;
    }

    private String onChange;
}
