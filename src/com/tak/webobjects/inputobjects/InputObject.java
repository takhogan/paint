package com.tak.webobjects.inputobjects;

public class InputObject {
    public InputObject(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    //blank class
    private String objectType;
}
