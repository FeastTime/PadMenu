package com.feasttime.dishmap.rxbus.event;

/**
 * Created by chen on 2017/10/29.
 */

public class WebSocketEvent {
    public static final int RECEIVE_SERVER_DATA = 1;

    public String jsonData;

    public int eventType;

    public WebSocketEvent(int eventType,String jsonData){
        this.eventType = eventType;
        this.jsonData = jsonData;
    }

    public WebSocketEvent(int eventType) {
        this.eventType = eventType;
    }
}
