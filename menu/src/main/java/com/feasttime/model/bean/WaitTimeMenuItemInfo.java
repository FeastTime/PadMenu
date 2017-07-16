package com.feasttime.model.bean;



public class WaitTimeMenuItemInfo {

    // 菜品名称
    private String dishName;
    // 需要时长
    private long needTime;
    // 开始时间
    private long startTime;
    // 状态 0:未开始 1:下锅 2完成
    private byte state;

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public long getNeedTime() {
        return needTime;
    }

    public void setNeedTime(long needTime) {
        this.needTime = needTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
}
