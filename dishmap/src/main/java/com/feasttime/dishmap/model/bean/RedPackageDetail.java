package com.feasttime.dishmap.model.bean;

/**
 *
 * Created by Li on 2018/3/7.
 */

public class RedPackageDetail {

    // 是否最佳-是
    public static final int ISBESTLUCK_TRUE = 1;

    // 是否最佳-否
    public static final int ISBESTLUCK_FALSE = 2;

    int isBestLuck;

    String nickName;

    String redPackageId;

    String userIcon;

    long unpackTime;

    String redPackageTitle;


    public int getIsBestLuck() {
        return isBestLuck;
    }

    public void setIsBestLuck(int isBestLuck) {
        this.isBestLuck = isBestLuck;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRedPackageId() {
        return redPackageId;
    }

    public void setRedPackageId(String redPackageId) {
        this.redPackageId = redPackageId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public long getUnpackTime() {
        return unpackTime;
    }

    public void setUnpackTime(long unpackTime) {
        this.unpackTime = unpackTime;
    }

    public String getRedPackageTitle() {
        return redPackageTitle;
    }

    public void setRedPackageTitle(String redPackageTitle) {
        this.redPackageTitle = redPackageTitle;
    }
}
