package com.tak.pojo;
import com.tak.webobjects.FinanceObject;
import com.tak.webobjects.inputobjects.*;

import java.util.ArrayList;

public class Order{
    public Order(int order_id, String owner_name, String wantname, double wantamount, String givename, double giveamount) {
        this.order_id = order_id;
        this.owner_name = owner_name;
        this.wantname = wantname;
        this.wantamount = wantamount;
        this.givename = givename;
        this.giveamount = giveamount;
    }

    public Order(String owner_name, String wantname, double wantamount, String givename, double giveamount){
        this.owner_name = owner_name;
        this.wantname = wantname;
        this.wantamount = wantamount;
        this.givename = givename;
        this.giveamount = giveamount;
    }
    public Order(){

    }

    public Order(Order o){
        this.order_id = o.order_id;
        this.owner_name = o.owner_name;
        this.wantname = o.wantname;
        this.wantamount = o.wantamount;
        this.givename = o.givename;
        this.giveamount = o.giveamount;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getWantname() {
        return wantname;
    }

    public void setWantname(String wantname) {
        this.wantname = wantname;
    }

    public double getWantamount() {
        return wantamount;
    }

    public void setWantamount(double wantamount) {
        this.wantamount = wantamount;
    }

    public String getGivename() {
        return givename;
    }

    public void setGivename(String givename) {
        this.givename = givename;
    }

    public double getGiveamount() {
        return giveamount;
    }

    public void setGiveamount(double giveamount) {
        this.giveamount = giveamount;
    }

    private int order_id;
    private String owner_name;
    private String wantname;
    private double wantamount;
    private String givename;
    private double giveamount;


    public String toString(){
        return ("[" + this.order_id + "|" + this.owner_name + "|" + this.wantname + "-" + this.wantamount + "|" +
                this.givename + "-" + this.giveamount + "]");
    }

    public static String getFormName(){
        return "/currencygen";
    }

    public static String[] getVarNames(){
        String[] varNames = new String[6];
        varNames[0] = "Order ID";
        varNames[1] = "Owner Name";
        varNames[2] = "Wanted Currency";
        varNames[3] = "Wanted Amount";
        varNames[4] = "Give Currency";
        varNames[5] = "Give Amount";
        return varNames;
    }
    public static String[] getInputVarNames(){
        String[] varNames = new String[4];
        varNames[0] = "Buying Currency";
        varNames[1] = "Buy Amount";
        varNames[2] = "Selling Currency";
        varNames[3] = "Sell Amount";
        return varNames;
    }

    public static ArrayList<InputObject> getInputFields(ArrayList<String> clist){
        ArrayList<InputObject> flist = new ArrayList<>(4);
        //Default Values
        String wantname = "Bitcoin";
        double wantamount = 10000;
        String givename = "Litecoin";
        double giveamount = 1000;

        flist.add(new SelectContainer("want", wantname, clist));
        flist.add(new TextInputField("wantamount", wantamount+""));
        flist.add(new SelectContainer("give", givename, clist));
        flist.add(new TextInputField("giveamount", giveamount+""));
        return flist;
    }
    public static ArrayList<InputObject> getInputFields(ArrayList<String> clist,
                                                        String wantname, double wantamount,
                                                        String givename, double giveamount){
        ArrayList<InputObject> flist = new ArrayList<>(4);
        //varNames, inputtype, defaultvalue
        flist.add(new SelectContainer("want", wantname, clist));
        flist.add(new TextInputField("wantamount", wantamount+""));
        flist.add(new SelectContainer("give", givename, clist));
        flist.add(new TextInputField("giveamount", giveamount+""));
        return flist;
    }

    public static ArrayList<FinanceObject> toFinanceObjectArr(ArrayList<Order> olist){
        ArrayList<FinanceObject> flist = new ArrayList<>();
        for(Order o : olist){
            flist.add(o.toFinanceObject(""));
        }
        return flist;
    }

    public FinanceObject toFinanceObject(String status){
        ArrayList<String> varVals = new ArrayList<>(6);
        varVals.add(""+this.getOrder_id());
        varVals.add(this.getOwner_name());
        varVals.add(this.getWantname());
        varVals.add(""+this.getWantamount());
        varVals.add(this.getGivename());
        varVals.add(""+this.getGiveamount());
        FinanceObject fo = new FinanceObject(status, this.order_id, varVals);
        return fo;
    }



}