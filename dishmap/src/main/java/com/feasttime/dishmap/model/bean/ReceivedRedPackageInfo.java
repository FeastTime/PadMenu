package com.feasttime.dishmap.model.bean;

/**
 *
 * Created by chen on 2018/2/28.
 */

public class ReceivedRedPackageInfo extends BaseResponseBean{

    //    1:抢光了
    public static final int RESULT_TYPE_EMPTY = 1;
    //    2:抢过了
    public static final int RESULT_TYPE_TAKED = 2;
    //    3:中奖了
    public static final int RESULT_TYPE_LUCKY = 3;
    //    4:未中奖
    public static final int RESULT_TYPE_UN_LUCKY = 4;

    String message;
    MyTableItemInfo tableInfo;
    CouponChildListItemInfo couponInfo;

    // 抢红包状态 1:抢光了  2:抢过了  3:中奖了  4:未中奖
    int takeRedPackageResultType;

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

    public int getTakeRedPackageResultType() {
        return takeRedPackageResultType;
    }

    public void setTakeRedPackageResultType(int takeRedPackageResultType) {
        this.takeRedPackageResultType = takeRedPackageResultType;
    }
}
