package com.tak.webobjects;
import java.util.ArrayList;

public class FinanceObject {

    public FinanceObject(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getVarVals() {
        return varVals;
    }

    public void setVarVals(ArrayList<String> varVals) {
        this.varVals = varVals;
    }


    public int getSubmitVal() {
        return submitVal;
    }

    public void setSubmitVal(int submitVal) {
        this.submitVal = submitVal;
    }


    public FinanceObject(String status, int submitVal, ArrayList<String> varVals) {
        this.status = status;
        this.submitVal = submitVal;
        this.varVals = varVals;
    }

    private String status;
    private int submitVal;
    private ArrayList<String> varVals;


    public String toString(){
        String header = "("+this.status+") [";
        for(String val : this.varVals){
            header += (val + ",");
        }
        header = header.substring(0, header.length()-1);
        header +="]";
        return header;
    }

}
