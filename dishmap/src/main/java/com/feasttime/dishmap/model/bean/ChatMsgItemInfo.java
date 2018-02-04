package com.feasttime.dishmap.model.bean;

/**
 * Created by chen on 2017/10/25.
 */

public class ChatMsgItemInfo {

    // 是否左边
    boolean isLeft;

    // 消息内容
    String msg;

    // 用户图标
    String icon;

    // 消息时间
    String time;

    // 红包id
    String redPackageId;

    // 是否红包
    boolean isRedPackage;

    // 红包是否点击过
    boolean isRedPackageUsed;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRedPackageId() {
        return redPackageId;
    }

    public void setRedPackageId(String redPackageId) {
        this.redPackageId = redPackageId;
    }

    public boolean isRedPackage() {
        return isRedPackage;
    }

    public void setRedPackage(boolean redPackage) {
        isRedPackage = redPackage;
    }

    public boolean isRedPackageUsed() {
        return isRedPackageUsed;
    }

    public void setRedPackageUsed(boolean redPackageUsed) {
        isRedPackageUsed = redPackageUsed;
    }
}
