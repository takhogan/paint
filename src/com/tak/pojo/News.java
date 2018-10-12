package com.tak.pojo;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class News {
    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }


    public News(String headline){
        if(headline.length()>140){
            this.headline = headline.substring(0, 139);
        } else {
            this.headline = headline;
        }
        this.eventtime = ZonedDateTime.now();
    }

    public News(String headline, ZonedDateTime eventtime){
        this.headline = headline;
        this.eventtime = eventtime;
    }

    public News (){

    }
    public String toString(){
        return "["+this.eventtime.format(DateTimeFormatter.ISO_LOCAL_TIME)+"|"+this.headline+"]";
    }

    private String headline;

    public ZonedDateTime getEventtime() {
        return eventtime;
    }

    public void setEventtime(ZonedDateTime eventtime) {
        this.eventtime = eventtime;
    }

    private ZonedDateTime eventtime;
}
