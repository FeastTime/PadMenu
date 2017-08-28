package com.feasttime.rxbus.event;

import com.feasttime.model.bean.OrderInfo;

/**
 * Created by chen on 2017/8/29.
 */

public class OrderEvent {
    public static final int ADD_ONE_DISHES = 1;
    public OrderInfo orderInfo;

    public int eventType;

    public OrderEvent(int eventType,OrderInfo orderInfo){
        this.eventType = eventType;
        this.orderInfo = orderInfo;
    }

    public OrderEvent(int eventType) {
        this.eventType = eventType;
    }
}
