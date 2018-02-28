package com.feasttime.dishmap.model.bean;

/**
 * Created by chen on 2018/2/28.
 */

public class ReceivedRedPackageInfo extends BaseResponseBean{
    String message;
    MyTableItemInfo tableInfo;
    CouponChildListItemInfo couponInfo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyTableItemInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(MyTableItemInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public CouponChildListItemInfo getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(CouponChildListItemInfo couponInfo) {
        this.couponInfo = couponInfo;
    }
}
