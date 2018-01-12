package com.feasttime.dishmap.model.bean;

import java.util.List;

/**
 * Created by chen on 2018/1/12.
 */

public class CouponListItemInfo {
    private String name;

    private List<CouponChildListItemInfo> childListItemInfos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CouponChildListItemInfo> getChildListItemInfos() {
        return childListItemInfos;
    }

    public void setChildListItemInfos(List<CouponChildListItemInfo> childListItemInfos) {
        this.childListItemInfos = childListItemInfos;
    }
}
