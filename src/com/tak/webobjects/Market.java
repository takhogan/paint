package com.tak.webobjects;
import java.util.List;
import com.tak.pojo.Order;

public class Market{

    public Market(String c_name, List<Order> buyorders, List<Order> sellorders) {
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

    public List<Order> getBuyorders() {
        return buyorders;
    }

    public void setBuyorders(List<Order> buyorders) {
        this.buyorders = buyorders;
    }

    public List<Order> getSellorders() {
        return sellorders;
    }

    public void setSellorders(List<Order> sellorders) {
        this.sellorders = sellorders;
    }

    private String c_name;
    private List<Order> buyorders;
    private List<Order> sellorders;

}