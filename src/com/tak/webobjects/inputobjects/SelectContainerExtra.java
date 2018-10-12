package com.tak.webobjects.inputobjects;

import java.util.ArrayList;

public class SelectContainerExtra extends SelectContainer {


    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }




    public SelectContainerExtra(String varName, NamedValue defaultValue, ArrayList<NamedValue> options, String style) {
        super(varName, defaultValue, options);
        this.style = style;

    }

    public SelectContainerExtra(String varName, String defaultValue, ArrayList<String> options, String style) {
        super(varName, defaultValue, options);
        this.style = style;

    }

    private String style;




}
