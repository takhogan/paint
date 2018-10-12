package com.tak.webobjects.inputobjects;
import java.util.ArrayList;

public class SelectContainer extends InputField{


    public SelectContainer(String varName, NamedValue defaultValue, ArrayList<NamedValue> options) {
        super("select", varName, defaultValue);
        this.options = options;
    }
    public SelectContainer(String varName, String defaultValue, ArrayList<String> options) {
        super("select", varName, NamedValue.toNamedValue(defaultValue));
        this.options = NamedValue.toNamedValueArrList(options);
    }



    public ArrayList<NamedValue> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<NamedValue> options) {
        this.options = options;
    }

    private ArrayList<NamedValue> options;

}
