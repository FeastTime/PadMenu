package com.feasttime.dishmap.model.bean;

import java.util.List;

/**
 * Created by chen on 2017/11/5.
 */

public class BidResultInfo {

    int resultCode;
    List<BidResultItem> data;
    String bid;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<BidResultItem> getData() {
        return data;
    }

    public void setData(List<BidResultItem> data) {
        this.data = data;
    }


}
