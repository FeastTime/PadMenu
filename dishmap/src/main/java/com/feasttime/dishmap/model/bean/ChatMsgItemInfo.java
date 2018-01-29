package com.feasttime.dishmap.model.bean;

/**
 * Created by chen on 2017/10/25.
 */

public class ChatMsgItemInfo {
    boolean isLeft;
    String msg;
    String icon;

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
