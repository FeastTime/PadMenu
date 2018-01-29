package com.feasttime.dishmap.rxbus.event;

/**
 * Created by chen on 2017/10/29.
 */

public class WebSocketEvent {

    // 目前  PRICE_RANK_CHANGE  与 BEFORE_TABLES_LIST 一样的type,PRICE_RANK_CHANGE可能没用
    public static final int WEBSOCKET_CONNECT_SERVER_SUCCESS = 0;  //websocket 连接服务器成功

    // 发送消息
    public static final int SEND_MESSAGE = 1;

    // 收到消息
    public static final int RECEIVED_MESSAGE = 2;

    // 发红包
    public static final int SEND_RED_PACKAGE = 3;

    // 收到红包
    public static final int RECEIVED_RED_PACKAGE = 4;

    // 打开红包
    public static final int OPEN_RED_PACKAGE = 5;

    // 获得红包奖励
    public static final int RECEIVED_RED_PACKAGE_SURPRISED = 6;

    // 用餐人数变更通知
    public static final int WAITING_USER_CHANGED_NOTIFY = 7;

    // 设置用餐人数
    public static final int SET_NUMBER_OF_USER = 8;






    public static final int USER_REACH_STORE = 11; //用户进店
    public static final int BOSS_PLACE_TABLE = 12; //老板放桌
    public static final int NEW_TABLE_NOTIFICATION = 13; //新桌位通知
    public static final int USER_BET_PRICE = 14; //用户出价
    public static final int USER_GRAP_TABLE = 15; //用户竞价桌位
    public static final int PRICE_RANK_CHANGE = 16; //价格排名变化
    public static final int BID_TABLE_RESULT_NOTIFICATION = 17; //竞价桌位结果通知
    public static final int BEFORE_TABLES_LIST = 16; //历史桌位列表接口
    public static final int GROB_RESULT_NOTIFICATION = 19; //桌位结果通知
    public static final int REQUEST_BEFORE_TABLES_LIST = 20; //请求历史桌位列表

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
