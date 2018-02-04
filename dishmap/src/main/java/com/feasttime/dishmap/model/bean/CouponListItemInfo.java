package com.feasttime.dishmap.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen on 2018/1/12.
 */

public class CouponListItemInfo implements Serializable{
    private String storeName;

    private List<CouponChildListItemInfo> dataList;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<CouponChildListItemInfo> getDataList() {
        return dataList;
    }

    public void setDataList(List<CouponChildListItemInfo> dataList) {
        this.dataList = dataList;
    }
}
