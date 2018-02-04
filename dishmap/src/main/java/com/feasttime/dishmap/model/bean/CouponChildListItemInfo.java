package com.feasttime.dishmap.model.bean;

import java.io.Serializable;

/**
 *
 * Created by chen on 2018/1/12.
 */

public class CouponChildListItemInfo implements Serializable{

    String couponId;

    String storeId;

    String couponCode;

    String couponTitle;

    String couponType;

    String couponValidity;

    String permissionsDescribed;

    String useTime;

    String userId;

    byte isUsed;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponValidity() {
        return couponValidity;
    }

    public void setCouponValidity(String couponValidity) {
        this.couponValidity = couponValidity;
    }

    public String getPermissionsDescribed() {
        return permissionsDescribed;
    }

    public void setPermissionsDescribed(String permissionsDescribed) {
        this.permissionsDescribed = permissionsDescribed;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(byte isUsed) {
        this.isUsed = isUsed;
    }
}
