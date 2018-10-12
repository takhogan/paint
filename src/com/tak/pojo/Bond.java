package com.tak.pojo;
import com.tak.webobjects.inputobjects.*;
import com.tak.webobjects.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Bond {
    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getBorrower_name() {
        return borrower_name;
    }

    public void setBorrower_name(String borrower_name) {
        this.borrower_name = borrower_name;
    }

    public String getLoan_currency() {
        return loan_currency;
    }

    public void setLoan_currency(String loan_currency) {
        this.loan_currency = loan_currency;
    }

    public double getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(double loan_amount) {
        this.loan_amount = loan_amount;
    }

    public double getInitial_margin() {
        return initial_margin;
    }

    public void setInitial_margin(double initial_margin) {
        this.initial_margin = initial_margin;
    }

    public int getInterest_frequency() {
        return interest_frequency;
    }

    public void setInterest_frequency(int interest_frequency) {
        this.interest_frequency = interest_frequency;
    }

    public String getInterest_currency() {
        return interest_currency;
    }

    public void setInterest_currency(String interest_currency) {
        this.interest_currency = interest_currency;
    }

    public double getInterest_amount() {
        return interest_amount;
    }

    public void setInterest_amount(double interest_amount) {
        this.interest_amount = interest_amount;
    }

    public int getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(int loan_type) {
        this.loan_type = loan_type;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public ZonedDateTime getLast_payment() {
        return last_payment;
    }

    public void setLast_payment(ZonedDateTime last_payment) {
        this.last_payment = last_payment;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }


    private int loan_id;
    private int loan_type;
    private String owner_name;
    private String borrower_name;
    private String loan_currency;
    private double loan_amount;
    private double initial_margin;
    private int interest_frequency; //in minutes
    private String interest_currency;
    private double interest_amount; //last/starting payment
    private long expiration; //seconds to expiration
    private ZonedDateTime last_payment;
    private short active;

    public Bond(){

    }

    public Bond(String user_name, int loanorborrow, String c_name,
                double c_amount, double initial_margin, int frequency, int loan_type,
                String interest_currency, double interest_amount, long expiry){
        if(loanorborrow == 1){ //lender
            this.owner_name = user_name;
            this.borrower_name = null;
        } else if (loanorborrow == 2){
            this.borrower_name = user_name;
            this.owner_name = null;
        }
        this.loan_currency = c_name;
        this.loan_amount = c_amount;
        this.initial_margin = initial_margin;
        this.interest_frequency = frequency;
        this.interest_currency = interest_currency;
        this.interest_amount = interest_amount;
        this.loan_type = loan_type;
        this.expiration = expiry;
        this.last_payment = ZonedDateTime.now();
        this.active = 0;
    }

    public String toString(){
        return "["+this.loan_id+":"+this.owner_name+"//"+this.borrower_name+"|"+this.loan_currency+":$"+this.loan_amount+
                "-req:$"+this.initial_margin+"|"+this.interest_frequency+"minutes:$"+this.interest_amount+"|t:"+this.loan_type+"|"+(this.expiration/60)+
                "m|"+this.active+"]";
    }

    public static String getFormName(){
        return "/bondgen";
    }

    public static String[] getVarNames(){
        String[] varNames = new String[12];
        varNames[0] = "Loan ID";
        varNames[1] = "Owner Name";
        varNames[2] = "Borrower Name";
        varNames[3] = "Base Currency";
        varNames[4] = "Loan Amount";
        varNames[5] = "Intial Margin";
        varNames[6] = "Interest Frequency (minutes)";
        varNames[7] = "Interest Payment (last)";
        varNames[8] = "Loan Type";
        varNames[9] = "Expiration";
        //not relevant
        varNames[10] = "Last Payment";
        varNames[11] = "Active?";
        //active is a server side variable
        return varNames;

    }

    public static String[] getLendVarNames(){
        String[] varNames = new String[9];
        varNames[0] = "Loan ID";
        varNames[1] = "Lender Name";
        //varNames[] = "Borrower Name";
        varNames[2] = "Base Currency";
        varNames[3] = "Loan Amount";
        varNames[4] = "Intial Margin";
        varNames[5] = "Interest Frequency (minutes)";
        varNames[6] = "Interest Payment (last)";
        varNames[7] = "Loan Type";
        varNames[8] = "Expiration";
        return varNames;
    }

    public static String[] getBorrowVarNames(){
        String[] varNames = new String[9];
        varNames[0] = "Loan ID";
        //varNames[] = "Lender Name";
        varNames[1] = "Borrower Name";
        varNames[2] = "Base Currency";
        varNames[3] = "Loan Amount";
        varNames[4] = "Intial Margin";
        varNames[5] = "Interest Frequency (minutes)";
        varNames[6] = "Interest Payment (last)";
        varNames[7] = "Loan Type";
        varNames[8] = "Expiration";
        return varNames;
    }



    public static String[] getInputVarNames(){
        String[] varNames = new String[8];
        varNames[0] = "Borrow/Loan";
        varNames[1] = "Base Currency";
        varNames[2] = "Loan Amount";
        varNames[3] = "Initial Margin";
        varNames[4] = "Interest Amount";
        varNames[5] = "Repayment Frequency";
        varNames[6] = "Loan Type";
        varNames[7] = "Loan Term";
        return varNames;

    }

    public static ArrayList<NamedValue> getCreateTypeOptions(){
        ArrayList<NamedValue> nlist = new ArrayList<>(2);
        nlist.add(new NamedValue("lend", "1"));
        nlist.add(new NamedValue("borrow", "2"));
        return nlist;
    }

    public static ArrayList<NamedValue> getFrequencyOptions(){
        ArrayList<NamedValue> nlist = new ArrayList<>(3);
        nlist.add(new NamedValue("daily", "1440"));
        nlist.add(new NamedValue("hourly", "60"));
        nlist.add(new NamedValue("minutely", "1"));
        return nlist;
    }

    public static ArrayList<NamedValue> getLoanTypes(){
        ArrayList<NamedValue> nlist = new ArrayList<>(3);
        nlist.add(new NamedValue("bond", "1"));
        nlist.add(new NamedValue("constant repayment", "2"));
        nlist.add(new NamedValue("custom", "3"));
        return nlist;
    }
    public static ArrayList<NamedValue> getExpirationUnits(){
        ArrayList<NamedValue> nlist = new ArrayList<>(3);
        nlist.add(new NamedValue("days", "1440"));
        nlist.add(new NamedValue("hours", "60"));
        nlist.add(new NamedValue("minutes", "1"));
        return nlist;
    }

    public static ArrayList<InputObject> getInputFields(ArrayList<String> clist){
        ArrayList<InputObject> flist = new ArrayList<>(8);
        //InputField: varName, inputType, defaultType
        //SelectContainer: varName, defaultValue, Options
        //ISwFunc varName, inputType, defaultValue, inputStyle, selectName, options, selectStyle, funcVal
        ArrayList<NamedValue> toptions = Bond.getCreateTypeOptions();
        flist.add(new SelectContainer("create_type", toptions.get(0), toptions));
        flist.add(new SelectContainer("c_name", "Bitcoin", clist));
        flist.add(new TextInputField("c_amount", "0"));
        flist.add(new TextInputField("initial_margin", "0"));
        flist.add(new TextInputField("i_amount", "0"));
        InputField f = new TextInputFieldExtra( "freq_mult", "1","display:none");
        ArrayList<NamedValue> freqlist = Bond.getFrequencyOptions();
        SelectContainer sc = new SelectContainerFExtra("frequency", freqlist.get(0), freqlist,
                "display:inline-block;margin:0px;padding:0px","customFrequency(this)");
        flist.add(new InputSelect("input-select-onchange", f, sc));
        ArrayList<NamedValue> ltypes = Bond.getLoanTypes();
        flist.add(new SelectContainer("loan_type", ltypes.get(0), ltypes));
        InputField f2 = new TextInputFieldExtra("expiration_value", "1","display:inline-block");
        ArrayList<NamedValue> eunits = Bond.getExpirationUnits();
        SelectContainer sc2 = new SelectContainerExtra("expiration_units", eunits.get(0),
                eunits, "display:inline-block");
        flist.add(new InputSelect("input-select", f2, sc2));
        return flist;
    }

    public FinanceObject toFinanceObject(String status){
        ArrayList<String> varVals = new ArrayList<>(12);
        varVals.add(""+this.loan_id);
        varVals.add(this.owner_name);
        varVals.add(this.borrower_name);
        varVals.add(this.loan_currency);
        varVals.add(""+this.loan_amount);
        varVals.add(""+this.initial_margin);
        varVals.add(""+this.interest_frequency);
        varVals.add(""+this.interest_amount);
        varVals.add(""+this.loan_type);
        varVals.add(""+this.expiration);
        //not relevant variables
        varVals.add(""+this.last_payment);
        varVals.add(""+this.active);
        //active is a server side variable
        FinanceObject fo = new FinanceObject(status, this.loan_id,varVals);
        return fo;
    }

    public FinanceObject toLendFinanceObject(String status){
        ArrayList<String> varVals = new ArrayList<>(9);
        varVals.add(""+this.loan_id);
        varVals.add(this.owner_name);
        //varVals.add(this.borrower_name);
        varVals.add(this.loan_currency);
        varVals.add(""+this.loan_amount);
        varVals.add(""+this.initial_margin);
        varVals.add(""+this.interest_frequency);
        varVals.add(""+this.interest_amount);
        varVals.add(""+this.loan_type);
        varVals.add(""+this.expiration);
        //not relevant variables
        //varVals.add(""+this.last_payment);
        //varVals.add(""+this.active);
        //active is a server side variable
        FinanceObject fo = new FinanceObject(status, this.loan_id,varVals);
        return fo;
    }

    public FinanceObject toBorrowFinanceObject(String status){
        ArrayList<String> varVals = new ArrayList<>(9);
        varVals.add(""+this.loan_id);
        //varVals.add(this.owner_name);
        varVals.add(this.borrower_name);
        varVals.add(this.loan_currency);
        varVals.add(""+this.loan_amount);
        varVals.add(""+this.initial_margin);
        varVals.add(""+this.interest_frequency);
        varVals.add(""+this.interest_amount);
        varVals.add(""+this.loan_type);
        varVals.add(""+this.expiration);
        //not relevant variables
        //varVals.add(""+this.last_payment);
        //varVals.add(""+this.active);
        //active is a server side variable
        FinanceObject fo = new FinanceObject(status, this.loan_id,varVals);
        return fo;
    }

    public static ArrayList<FinanceObject> toFinanceObjectArr(ArrayList<Bond> blist){
        ArrayList<FinanceObject> flist = new ArrayList<>();
        for(Bond b : blist){
            flist.add(b.toFinanceObject(""));
        }
        return flist;
    }

}
