package com.tak.webobjects;
import com.tak.pojo.Order;

import java.util.ArrayList;

public class PendingOrder extends Order {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public PendingOrder(Order o, String status){
        this.setOrder_id(o.getOrder_id());
        this.setOwner_name(o.getOwner_name());
        this.setWantname(o.getWantname());
        this.setWantamount(o.getWantamount());
        this.setGivename(o.getGivename());
        this.setGiveamount(o.getGiveamount());
        this.status = status;
    }
    public FinanceObject toFinanceObject(){
        ArrayList<String> varVals = new ArrayList<>(6);
        varVals.add(""+this.getOrder_id());
        varVals.add(this.getOwner_name());
        varVals.add(this.getWantname());
        varVals.add(""+this.getWantamount());
        varVals.add(this.getGivename());
        varVals.add(""+this.getGiveamount());
        FinanceObject fo = new FinanceObject(this.status, this.getOrder_id(), varVals);
        return fo;
    }
}
