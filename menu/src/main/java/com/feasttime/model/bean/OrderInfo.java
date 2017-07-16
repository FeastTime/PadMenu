package com.feasttime.model.bean;

import java.util.List;

/**
 * Created by chen on 2017/5/11.
 */

public class OrderInfo {
    int resultCode;
    String totalPrice;
    String discountSale;
    List<MyOrderListItemInfo> myOrderList;
    List<RecommendOrderListItemInfo> recommendOrderList;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDiscountSale() {
        return discountSale;
    }

    public void setDiscountSale(String discountSale) {
        this.discountSale = discountSale;
    }

    public List<MyOrderListItemInfo> getMyOrderList() {
        return myOrderList;
    }

    public void setMyOrderList(List<MyOrderListItemInfo> myOrderList) {
        this.myOrderList = myOrderList;
    }

    public List<RecommendOrderListItemInfo> getRecommendOrderList() {
        return recommendOrderList;
    }

    public void setRecommendOrderList(List<RecommendOrderListItemInfo> recommendOrderList) {
        this.recommendOrderList = recommendOrderList;
    }
}
