package com.tak.webobjects.inputobjects;
import java.util.ArrayList;

public class NamedValue {
    public NamedValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String name;
    private String value;

    public static NamedValue toNamedValue(String s){
        return new NamedValue(s,s);
    }

    public static ArrayList<NamedValue> toNamedValueArrList(ArrayList<String> slist){
        ArrayList<NamedValue> nlist = new ArrayList<>(slist.size());
        for(String s : slist){
            nlist.add(new NamedValue(s,s));
        }
        return nlist;
    }

    public boolean equals(NamedValue nv){
        return (nv.getName().equals(this.name) && nv.getValue().equals(this.value));

    }

}
