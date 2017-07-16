package com.feasttime.model.bean;


import java.util.List;

public class WaitTimeMenuInfo {

    // 下单时间
    private long orderTime;

    // 需要时间
    private long needTime;

    // 状态  0:未下单 1:已下单 2:已完成
    private byte state;

    // 菜品列表
    private List<WaitTimeMenuItemInfo> list;

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public long getNeedTime() {
        return needTime;
    }

    public void setNeedTime(long needTime) {
        this.needTime = needTime;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public List<WaitTimeMenuItemInfo> getList() {
        return list;
    }

    public void setList(List<WaitTimeMenuItemInfo> list) {
        this.list = list;
    }
}
