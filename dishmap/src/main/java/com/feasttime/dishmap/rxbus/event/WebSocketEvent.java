package com.feasttime.dishmap.rxbus.event;

/**
 * Created by chen on 2017/10/29.
 */

public class WebSocketEvent {
    public static final int USER_REACH_STORE = 1; //用户进店
    public static final int BOSS_PLACE_TABLE = 2; //老板放桌
    public static final int NEW_TABLE_NOTIFICATION = 3; //新桌位通知
    public static final int USER_BET_PRICE = 4; //用户出价
    public static final int USER_GRAP_TABLE = 5; //用户抢桌位
    public static final int PRICE_RANK_CHANGE = 6; //价格排名变化
    public static final int GRAP_TABLE_RESULT_NOTIFICATION = 7; //抢桌位结果通知
    public static final int BEFORE_TABLES_LIST = 8; //历史桌位列表接口

    public String jsonData;

    public int eventType = 0;

    public WebSocketEvent(int eventType,String jsonData){
        this.eventType = eventType;
        this.jsonData = jsonData;
    }

    public WebSocketEvent(int eventType) {
        this.eventType = eventType;
    }
}
