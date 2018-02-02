package com.feasttime.dishmap.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2018/1/25.
 */

public class CouponInfo {
    int resultCode;
    String resultMsg;
    ArrayList<CouponListItemInfo> couponList;

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

    public ArrayList<CouponListItemInfo> getCouponList() {
        return couponList;
    }

    public void setCouponList(ArrayList<CouponListItemInfo> couponList) {
        this.couponList = couponList;
    }
}
