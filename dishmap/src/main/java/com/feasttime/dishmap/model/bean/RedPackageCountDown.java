package com.feasttime.dishmap.model.bean;


/**
 *
 * Created by li on 2018/1/26.
 */

public class RedPackageCountDown extends BaseResponseBean{

    long countDownTime;

    boolean isCountDown;

    public long getCountDownTime() {
        return countDownTime;
    }

    public void setCountDownTime(long countDownTime) {
        this.countDownTime = countDownTime;
    }

    public boolean isCountDown() {
        return isCountDown;
    }

    public void setCountDown(boolean countDown) {
        isCountDown = countDown;
    }
}
