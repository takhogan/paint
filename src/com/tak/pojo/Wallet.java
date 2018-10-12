package com.tak.pojo;
import com.tak.webobjects.FinanceObject;

import java.util.ArrayList;
public class Wallet{
    public int getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(int wallet_id) {
        this.wallet_id = wallet_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static double MAXVAL(){return 999999.99;}
    public Wallet(int wallet_id, String user_name, String c_name, double balance) {
        this.wallet_id = wallet_id;
        this.user_name = user_name;
        this.c_name = c_name;
        this.balance = balance;
    }

    private int wallet_id;
    private String user_name;
    private String c_name;
    private double balance;

    public String toString(){
        return "[" + this.user_name + "|" + this.c_name + ": " + this.balance + "]";
    }


    public Wallet(String user_name, String c_name, double balance){
        this.user_name = user_name;
        this.c_name = c_name;
        this.balance = balance;
    }
    public Wallet(String user_name, String c_name){
        this.user_name = user_name;
        this.c_name = c_name;
    }
    public Wallet dup(){
        return new Wallet(this.wallet_id, this.user_name, this.c_name, this.balance);

    }
    public Wallet(){ //mybatis needs this

    }
    public static String[] getVarNames(){
        String[] varNames = new String[4];
        varNames[0] = "Wallet ID";
        varNames[1] = "Wallet Owner";
        varNames[2] = "Wallet Currency";
        varNames[3] = "Wallet Balance";
        return varNames;
    }

    public FinanceObject toFinanceObject(String status){
        ArrayList<String> varVals = new ArrayList<>(4);
        varVals.add(""+this.getWallet_id());
        varVals.add(this.getUser_name());
        varVals.add(this.getC_name());
        varVals.add(""+this.getBalance());
        FinanceObject fo = new FinanceObject(status, this.getWallet_id(), varVals);
        return fo;
    }




    /*

    public Wallet(int user_id, String username, String c_name, BigDecimal bd){
        this.user_id = user_id;
        this.username = username;
        this.c_name = c_name;
        this.balance = bd.doubleValue();
    }
    public Wallet(int user_id, String username, String c_name, double supply){
        this.user_id = user_id;
        this.username = username;
        this.c_name = c_name;
        this.balance = supply;
    }

    public String getCName(){
        return this.c_name;
    }
    public String getUserName(){ return this.username;}

    public Wallet dupc(String username, double supply){
        Wallet c = new Wallet(this.user_id, username, this.c_name, supply);
        return c;
    }
    public Wallet dup(double supply){
        Wallet c = new Wallet(this.user_id, this.username, this.c_name, supply);
        return c;
    }

    public double getBalance(){
        return this.balance;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }
    public String toString(){
        return ("[" + this.username + "|this.c_name: " + balance + "]");
    }
    */
}