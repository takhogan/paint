package com.tak.pojo;
public class Currency {
    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public short getC_trading() {
        return c_trading;
    }

    public void setC_trading(short c_trading) {
        this.c_trading = c_trading;
    }

    public short getC_b_trading() {
        return c_b_trading;
    }

    public void setC_b_trading(short c_b_trading) {
        this.c_b_trading = c_b_trading;
    }

    public short getC_f_trading() {
        return c_f_trading;
    }

    public void setC_f_trading(short c_f_trading) {
        this.c_f_trading = c_f_trading;
    }

    public Currency(){

    }

    public Currency(String c_name, short c_trading, short c_b_trading, short c_f_trading) {
        this.c_name = c_name;
        this.c_trading = c_trading;
        this.c_b_trading = c_b_trading;
        this.c_f_trading = c_f_trading;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public Currency(int c_id, String c_name, short c_trading, short c_b_trading, short c_f_trading) {
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_trading = c_trading;
        this.c_b_trading = c_b_trading;
        this.c_f_trading = c_f_trading;
    }

    private int c_id;
    private String c_name;
    private short c_trading;
    private short c_b_trading;
    private short c_f_trading;

}
