package com.feasttime.dishmap.model.bean;

/**
 * Created by chen on 2018/1/19.
 */

public class MessageItemInfo {
    String name;
    String message;
    String time;
    int msgCount;
    String storeId;
    String iconUrl;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
