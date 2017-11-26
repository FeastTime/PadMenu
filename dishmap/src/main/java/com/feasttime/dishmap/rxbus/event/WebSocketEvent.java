package com.feasttime.dishmap.rxbus.event;

/**
 * Created by chen on 2017/10/29.
 */

public class WebSocketEvent {

    // 目前  PRICE_RANK_CHANGE  与 BEFORE_TABLES_LIST 一样的type,PRICE_RANK_CHANGE可能没用
    public static final int WEBSOCKET_CONNECT_SERVER_SUCCESS = 0;  //websocket 连接服务器成功
    public static final int USER_REACH_STORE = 1; //用户进店
    public static final int BOSS_PLACE_TABLE = 2; //老板放桌
    public static final int NEW_TABLE_NOTIFICATION = 3; //新桌位通知
    public static final int USER_BET_PRICE = 4; //用户出价
    public static final int USER_GRAP_TABLE = 5; //用户竞价桌位
    public static final int PRICE_RANK_CHANGE = 6; //价格排名变化
    public static final int BID_TABLE_RESULT_NOTIFICATION = 7; //竞价桌位结果通知
    public static final int BEFORE_TABLES_LIST = 6; //历史桌位列表接口
    public static final int GROB_RESULT_NOTIFICATION = 9; //桌位结果通知
    public static final int REQUEST_BEFORE_TABLES_LIST = 10; //请求历史桌位列表

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
