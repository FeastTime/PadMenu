package com.feasttime.dishmap.model.bean;

/**
 * Created by chen on 2017/10/25.
 */

public class ChatMsgItemInfo {
    boolean isLeft;
    String msg;
    int icon;

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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
