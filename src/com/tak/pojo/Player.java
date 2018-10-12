package com.tak.pojo;

public class Player {

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int user_id;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Player(String user_name, int mode) {
        this.user_name = user_name;
        this.mode = mode;
    }

    public String user_name;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int mode;

    public Player(){ //mybatis uses this

    }
}

