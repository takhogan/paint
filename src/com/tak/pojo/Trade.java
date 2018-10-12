package com.tak.pojo;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Trade {
    public Trade(int order_id, String owner_name, String counter_party, String wantname, double wantamount,
                 String givename, double giveamount, ZonedDateTime execution_time) {
        this.order_id = order_id;
        this.owner_name = owner_name;
        this.counter_party = counter_party;
        this.wantname = wantname;
        this.wantamount = wantamount;
        this.givename = givename;
        this.giveamount = giveamount;
        this.execution_time = execution_time;
    }
    public Trade(Order o, String counter_party){
        this.order_id = o.getOrder_id();
        this.owner_name = o.getOwner_name();
        this.counter_party = counter_party;
        this.wantname = o.getWantname();
        this.wantamount = o.getWantamount();
        this.givename = o.getGivename();
        this.giveamount = o.getGiveamount();
        this.execution_time = ZonedDateTime.now();
    }
    public Trade(){};

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

    public String getCounter_party() {
        return counter_party;
    }

    public void setCounter_party(String counter_party) {
        this.counter_party = counter_party;
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

    public ZonedDateTime getExecution_time() {
        return execution_time;
    }

    public void setExecution_time(ZonedDateTime execution_time) {
        this.execution_time = execution_time;
    }

    private int order_id;
    private String owner_name;
    private String counter_party;
    private String wantname;
    private double wantamount;
    private String givename;
    private double giveamount;
    private ZonedDateTime execution_time;

    public String toString(){
        return ("[" + this.execution_time.format(DateTimeFormatter.ISO_LOCAL_TIME) +
                " | " + (this.wantamount/this.giveamount) + " " +  this.wantname + "/" + this.givename + "]" +
                " - (" + this.counter_party + ")");
    }
}
