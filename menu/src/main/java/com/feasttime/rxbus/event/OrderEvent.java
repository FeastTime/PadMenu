package com.feasttime.rxbus.event;

import com.feasttime.model.bean.MenuItemInfo;
import com.feasttime.model.bean.OrderInfo;

/**
 * Created by chen on 2017/8/29.
 */

public class OrderEvent {
    public static final int ADD_ONE_DISHES = 1;
    public static final int REMOVE_ONE_DISHES = 2;
    public static final int REFRESH_ORDER_NUMBER = 3;
    public MenuItemInfo menuItemInfo;

    public int eventType;

    public OrderEvent(int eventType,MenuItemInfo menuItemInfo){
        this.eventType = eventType;
        this.menuItemInfo = menuItemInfo;
    }

    public OrderEvent(int eventType) {
        this.eventType = eventType;
    }
}
