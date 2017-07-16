/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.model.bean;

import java.util.List;

public class DishesCategoryInfo {
    /**
     * resultCode : 0
     * dishesCategoryList : [{"categoryName":"今日特价","categoryID":"1"},{"categoryName":"羊肉","categoryID":"2"},{"categoryName":"牛肉","categoryID":"3"},{"categoryName":"猪肉","categoryID":"4"}]
     */

    private int resultCode;
    private List<DishesCategoryListBean> dishesCategoryList;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<DishesCategoryListBean> getDishesCategoryList() {
        return dishesCategoryList;
    }

    public void setDishesCategoryList(List<DishesCategoryListBean> dishesCategoryList) {
        this.dishesCategoryList = dishesCategoryList;
    }

    public static class DishesCategoryListBean {
        /**
         * categoryName : 今日特价
         * categoryID : 1
         */

        private String categoryName;
        private String categoryID;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryID() {
            return categoryID;
        }

        public void setCategoryID(String categoryID) {
            this.categoryID = categoryID;
        }
    }
}
