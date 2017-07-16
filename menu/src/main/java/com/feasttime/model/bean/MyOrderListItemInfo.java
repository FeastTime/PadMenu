package com.feasttime.model.bean;

/**
 * Created by chen on 2017/5/11.
 */

public class MyOrderListItemInfo {
    String dishID;
    String dishNO;
    String dishName;
    String dishImgUrl;
    String todayPrice;
    String amount;
    String price;
    String extraFlag;

    public String getDishID() {
        return dishID;
    }

    public void setDishID(String dishID) {
        this.dishID = dishID;
    }

    public String getDishNO() {
        return dishNO;
    }

    public void setDishNO(String dishNO) {
        this.dishNO = dishNO;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishImgUrl() {
        return dishImgUrl;
    }

    public void setDishImgUrl(String dishImgUrl) {
        this.dishImgUrl = dishImgUrl;
    }

    public String getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(String todayPrice) {
        this.todayPrice = todayPrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExtraFlag() {
        return extraFlag;
    }

    public void setExtraFlag(String extraFlag) {
        this.extraFlag = extraFlag;
    }
}
