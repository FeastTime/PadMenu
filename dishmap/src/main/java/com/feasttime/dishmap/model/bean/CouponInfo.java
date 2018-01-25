package com.feasttime.dishmap.model.bean;

import java.util.List;

/**
 * Created by chen on 2018/1/25.
 */

public class CouponInfo {
    int resultCode;
    String resultMsg;
    List<CouponListItemInfo> couponList;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public List<CouponListItemInfo> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponListItemInfo> couponList) {
        this.couponList = couponList;
    }
}
