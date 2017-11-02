package com.feasttime.dishmap.model.bean;

import java.util.List;

/**
 * Created by chen on 2017/11/1.
 */

public class PriceChangeInfo {
    String storeID;
    String type;
    List<PriceChangeItemInfo> detail;

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PriceChangeItemInfo> getDetail() {
        return detail;
    }

    public void setDetail(List<PriceChangeItemInfo> detail) {
        this.detail = detail;
    }
}
