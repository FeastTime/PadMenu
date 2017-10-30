package com.feasttime.dishmap.model;

/**
 * Created by chen on 2017/10/29.
 */

public class WebSocketConfig {
    public static final String wsUrl = "ws://47.94.16.58:9798/feast-web/websocket";

    public static final String USER_REACH_STORE = "1"; //用户进店
    public static final String BOSS_PLACE_TABLE = "2"; //老板放桌
    public static final String NEW_TABLE_NOTIFICATION = "3"; //新桌位通知
    public static final String USER_BET_PRICE = "4"; //用户出价
    public static final String USER_GRAP_TABLE = "5"; //用户抢桌位
    public static final String PRICE_RANK_CHANGE = "6"; //价格排名变化
    public static final String GRAP_TABLE_RESULT_NOTIFICATION = "7"; //抢桌位结果通知
    public static final String BEFORE_TABLES_LIST = "8"; //历史桌位列表接口

}
