/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.model.bean;

import java.util.List;

public class ShoppingCartInfo {
    /**
     * resultCode : 0
     * totalPrice : 333
     * discountSale : 满100送10元
     * myOrderList : [{"dishID":"1","dishNO":"32","dishName":"32","dishImgUrl":"32","todayPrice":"32","amount":"32","price":"32","extraFlag":"0"},{"dishID":"2","dishNO":"332","dishName":"312","dishImgUrl":"http://www.ddd.213.png","todayPrice":"32","amount":"32","price":"32","extraFlag":"0"}]
     * recommendOrderList : [{"dishID":"1","dishNO":"32","dishName":"32","dishImgUrl":"32","todayPrice":"365","amount":"22","beforeOrderTimes":"3","extraFlag":"2"}]
     */

    private int resultCode;
    private String totalPrice;
    private String discountSale;
    private List<MyOrderListBean> myOrderList;
    private List<RecommendOrderListBean> recommendOrderList;

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

    public List<MyOrderListBean> getMyOrderList() {
        return myOrderList;
    }

    public void setMyOrderList(List<MyOrderListBean> myOrderList) {
        this.myOrderList = myOrderList;
    }

    public List<RecommendOrderListBean> getRecommendOrderList() {
        return recommendOrderList;
    }

    public void setRecommendOrderList(List<RecommendOrderListBean> recommendOrderList) {
        this.recommendOrderList = recommendOrderList;
    }

    public static class MyOrderListBean {
        /**
         * dishID : 1
         * dishNO : 32
         * dishName : 32
         * dishImgUrl : 32
         * todayPrice : 32
         * amount : 32
         * price : 32
         * extraFlag : 0
         */

        private String dishID;
        private String dishNO;
        private String dishName;
        private String dishImgUrl;
        private String todayPrice;
        private String amount;
        private String price;
        private String extraFlag;

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

    public static class RecommendOrderListBean {
        /**
         * dishID : 1
         * dishNO : 32
         * dishName : 32
         * dishImgUrl : 32
         * todayPrice : 365
         * amount : 22
         * beforeOrderTimes : 3
         * extraFlag : 2
         */

        private String dishID;
        private String dishNO;
        private String dishName;
        private String dishImgUrl;
        private String todayPrice;
        private String amount;
        private String beforeOrderTimes;
        private String extraFlag;

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

        public String getBeforeOrderTimes() {
            return beforeOrderTimes;
        }

        public void setBeforeOrderTimes(String beforeOrderTimes) {
            this.beforeOrderTimes = beforeOrderTimes;
        }

        public String getExtraFlag() {
            return extraFlag;
        }

        public void setExtraFlag(String extraFlag) {
            this.extraFlag = extraFlag;
        }
    }
}
