package com.tak.webobjects.inputobjects;

public class InputSelect extends InputObject {


    public SelectContainer getSelect() {
        return select;
    }

    public void setSelect(SelectContainer select) {
        this.select = select;
    }

    public InputField getInput() {
        return input;
    }

    public void setInput(InputField input) {
        this.input = input;
    }


    public InputSelect(String objType, InputField input, SelectContainer select) {
        super(objType);
        this.input = input;
        this.select = select;
    }

    private InputField input;
    private SelectContainer select;

}
