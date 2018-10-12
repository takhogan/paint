package com.tak.pojo;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import com.tak.webobjects.inputobjects.*;
import com.tak.webobjects.FinanceObject;

public class FuturesContract {

    public FuturesContract(){

    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
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

    public ZonedDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(ZonedDateTime expiration) {
        this.expiration = expiration;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }



    public FuturesContract(int contract_type, String user, String wantname, double wantamount,
                           String givename, double giveamount, ZonedDateTime expiration){
        this.contract_type = contract_type;
        this.creator = user;
        this.wantname = wantname;
        this.wantamount = wantamount;
        this.givename = givename;
        this.giveamount = giveamount;
        this.expiration = expiration;
        this.active = 0;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public int getContract_type() {
        return contract_type;
    }

    public void setContract_type(int contract_type) {
        this.contract_type = contract_type;
    }

    private int contract_id;
    private int contract_type;
    private String creator;
    private String consumer;
    private String wantname;
    private double wantamount;
    private String givename;
    private double giveamount;
    private ZonedDateTime expiration;
    private short active;

    public static String getFormName(){
        return "/futuresgen";
    }

    public static double requiredMargin(){
        return 0.05;
    }

    //creator must have 5% of the value in givecurrency of wantamount in givecurrency
    //same for either long/short
    //input is (givecurrencybalance, wantamount, giveamount/wantamount rate)
    public static String verifyMargin(double user_balance, double contract_value, double latest_rate){
        if(user_balance >= contract_value*latest_rate*FuturesContract.requiredMargin()){
            System.out.println("user_balance = " + user_balance);
            System.out.println("FuturesContract.requiredMargin() = " + FuturesContract.requiredMargin());
            System.out.println("contract_value = " + contract_value);
            System.out.println("latest_rate = " + latest_rate);
            return null;
        } else{
            return "user balance of "+user_balance + " is not enough to satisfy the required margin for the contract with"+
                    " a value of "+contract_value+" at a rate of "+latest_rate;
        }
    }



    public static ArrayList<NamedValue> getExpirationUnits(){
        ArrayList<NamedValue> nlist = new ArrayList<>(3);
        nlist.add(new NamedValue("days", "1440"));
        nlist.add(new NamedValue("hours", "60"));
        nlist.add(new NamedValue("minutes", "1"));
        return nlist;
    }

    public static String[] getVarNames(){
        String[] varNames = new String[9];
        varNames[0] = "Contract ID";
        varNames[1] = "Creator";
        varNames[2] = "Consumer";
        varNames[3] = "Buying Currency";
        varNames[4] = "Buying Amount";
        varNames[5] = "Selling Currency";
        varNames[6] = "Selling Amount";
        varNames[7] = "Expiration";
        varNames[8] = "Active?";
        return varNames;
    }

    public static String[] getLongVarNames(){
        String[] varNames = new String[7];
        varNames[0] = "Contract ID";
        varNames[1] = "Buyer Name";
        //varNames[1] = "Seller Name";
        varNames[2] = "Buying Currency";
        varNames[3] = "Buying Amount";
        varNames[4] = "Selling Currency";
        varNames[5] = "Selling Amount";
        varNames[6] = "Expiration";
        //varNames[7] = "Active?";
        return varNames;
    }

    public static String[] getShortVarNames(){
        String[] varNames = new String[7];
        varNames[0] = "Contract ID";
        //varNames[1] = "Buyer Name";
        varNames[1] = "Seller Name";
        varNames[2] = "Buying Currency";
        varNames[3] = "Buying Amount";
        varNames[4] = "Selling Currency";
        varNames[5] = "Selling Amount";
        varNames[6] = "Expiration";
        //varNames[7] = "Active?";
        return varNames;
    }



    public ArrayList<String> getVarVals(){
        ArrayList<String> varVals = new ArrayList<String>(9);
        varVals.add(this.contract_id + "");
        varVals.add(this.creator);
        varVals.add(this.consumer);
        varVals.add(this.wantname);
        varVals.add(this.wantamount + "");
        varVals.add(this.givename);
        varVals.add(this.giveamount +"");
        varVals.add(this.expiration +"");
        varVals.add(this.active +"");
        return varVals;
    }

    //Futures contracts will all settle hourly
    public ArrayList<String> getLongVarVals(){
        ArrayList<String> varVals = new ArrayList<String>(7);
        varVals.add(this.contract_id + "");
        varVals.add(this.creator);
        varVals.add(this.wantname);
        varVals.add(this.wantamount + "");
        varVals.add(this.givename);
        varVals.add(this.giveamount +"");
        varVals.add(this.expiration +"");
        return varVals;
    }

    public ArrayList<String> getShortVarVals(){
        return this.getLongVarVals();
    }

    public static String[] getInputVarNames(){
        String[] varNames = new String[6];
        varNames[0] = "Create Type";
        varNames[1] = "Buying Currency";
        varNames[2] = "Buying Amount";
        varNames[3] = "Selling Currency";
        varNames[4] = "Selling Amount";
        varNames[5] = "Hours to Expiration Hour";
        return varNames;
    }

    public static ArrayList<NamedValue> getCreateTypes(){
        ArrayList<NamedValue> nlist = new ArrayList<>(2);
        nlist.add(new NamedValue("long", "1"));
        nlist.add(new NamedValue("short", "2"));
        return nlist;
    }

    public static ArrayList<InputObject> getInputFields(ArrayList<String> clist){
        ArrayList<InputObject> ilist = new ArrayList<InputObject>(6);
        ArrayList<NamedValue> nlist = FuturesContract.getCreateTypes();
        ilist.add(new SelectContainer("cr_type", nlist.get(0), nlist));
        ilist.add(new SelectContainer("wantname", "Bitcoin", clist));
        ilist.add(new TextInputField("wantamount", "10000"));
        ilist.add(new SelectContainer("givename", "Litecoin", clist));
        ilist.add(new TextInputField("giveamount", "1000"));
        ilist.add(new TextInputField("expiration_value", "1"));
        return ilist;
    }

    public FinanceObject toFinanceObject(String status){
        FinanceObject fo = new FinanceObject(status, this.getContract_id(), this.getVarVals());
        return fo;
    }

    public FinanceObject toLongFinanceObject(String status){
        FinanceObject fo = new FinanceObject(status, this.getContract_id(), this.getLongVarVals());
        return fo;
    }
    public FinanceObject toShortFinanceObject(String status){
        FinanceObject fo = new FinanceObject(status, this.getContract_id(), this.getShortVarVals());
        return fo;
    }

    public static ArrayList<FinanceObject> toFinanceObjectArrList(ArrayList<FuturesContract> fclist){
        ArrayList<FinanceObject> flist = new ArrayList<>();
        for(FuturesContract fc : fclist){
            flist.add(fc.toFinanceObject(""));
        }
        return flist;
    }

}
