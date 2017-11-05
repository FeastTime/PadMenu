package com.feasttime.dishmap.model.bean;

/**
 * Created by chen on 2017/11/4.
 */

public class NewTableNofiticationinfo {
    String maxPerson;
    String minPerson;
    String storeID;
    String desc;
    String type;
    String bid;
    String timeLimit;

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(String maxPerson) {
        this.maxPerson = maxPerson;
    }

    public String getMinPerson() {
        return minPerson;
    }

    public void setMinPerson(String minPerson) {
        this.minPerson = minPerson;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
