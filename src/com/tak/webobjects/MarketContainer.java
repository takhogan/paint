package com.tak.webobjects;
import java.util.ArrayList;


//varNames & nVars are added separately
public class MarketContainer {
    public MarketContainer(String c_name, ArrayList<FinanceObject> buyorders, ArrayList<FinanceObject> sellorders) {
        this.c_name = c_name;
        this.buyorders = buyorders;
        this.sellorders = sellorders;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public ArrayList<FinanceObject> getBuyorders() {
        return buyorders;
    }

    public void setBuyorders(ArrayList<FinanceObject> buyorders) {
        this.buyorders = buyorders;
    }

    public ArrayList<FinanceObject> getSellorders() {
        return sellorders;
    }

    public void setSellorders(ArrayList<FinanceObject> sellorders) {
        this.sellorders = sellorders;
    }

    String c_name;
    ArrayList<FinanceObject> buyorders;
    //FinanceObject.varVals;
    ArrayList<FinanceObject> sellorders;
}
