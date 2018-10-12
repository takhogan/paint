package com.tak.webobjects;

public class CurrencyStatus {

    public CurrencyStatus(String c_name, double givesupply, double wantsupply, double balancesupply){
        this.c_name = c_name;
        this.givesupply = givesupply;
        this.wantsupply = wantsupply;
        this.balancesupply = balancesupply;
    }
    public CurrencyStatus(){

    }

    private String c_name;
    private double givesupply;
    private double wantsupply;
    private double balancesupply;

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public double getGivesupply() {
        return givesupply;
    }

    public void setGivesupply(double givesupply) {
        this.givesupply = givesupply;
    }

    public double getWantsupply() {
        return wantsupply;
    }

    public void setWantsupply(double wantsupply) {
        this.wantsupply = wantsupply;
    }

    public double getBalancesupply() {
        return balancesupply;
    }

    public void setBalancesupply(double balancesupply) {
        this.balancesupply = balancesupply;
    }

    public String toString(){
        return "["+this.c_name+"|os:"+this.givesupply+",bs:"+this.wantsupply+",as:"+this.balancesupply+"]";
    }
}
