package com.feasttime.dishmap.model.bean;

import java.io.Serializable;

/**
 * Created by chen on 2018/1/26.
 */

public class MyTableItemInfo implements Serializable{

    String tableId;

    int storeId;

    String suportSeatNumber;

    int passType;

    long maketableTime;

    long taketableTime;

    long recieveTime;

    int price;

    int isCome;

    String userId;

    String userPhone;

    String userIcon;

    String userNickName;

    String description;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getSuportSeatNumber() {
        return suportSeatNumber;
    }

    public void setSuportSeatNumber(String suportSeatNumber) {
        this.suportSeatNumber = suportSeatNumber;
    }

    public int getPassType() {
        return passType;
    }

    public void setPassType(int passType) {
        this.passType = passType;
    }

    public long getMaketableTime() {
        return maketableTime;
    }

    public void setMaketableTime(long maketableTime) {
        this.maketableTime = maketableTime;
    }

    public long getTaketableTime() {
        return taketableTime;
    }

    public void setTaketableTime(long taketableTime) {
        this.taketableTime = taketableTime;
    }

    public long getRecieveTime() {
        return recieveTime;
    }

    public void setRecieveTime(long recieveTime) {
        this.recieveTime = recieveTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIsCome() {
        return isCome;
    }

    public void setIsCome(int isCome) {
        this.isCome = isCome;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
