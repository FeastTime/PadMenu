package com.feasttime.dishmap.model.bean;

import java.io.Serializable;

/**
 *
 * Created by chen on 2018/1/12.
 */

public class CouponChildListItemInfo implements Serializable{

    String couponId;

    String storeId;

    String storeName;

    String couponCode;

    String couponTitle;

    String couponType;

    long couponValidity;

    String permissionsDescribed;

    String useTime;

    long startTime;

    String userId;

    byte isUse;

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

    public long getCouponValidity() {
        return couponValidity;
    }

    public void setCouponValidity(long couponValidity) {
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

    public byte getIsUse() {
        return isUse;
    }

    public void setIsUse(byte isUse) {
        this.isUse = isUse;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
