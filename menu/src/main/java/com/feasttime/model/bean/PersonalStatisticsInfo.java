/*
 * Copyright (c) 2017. sheng yan
 */



package com.feasttime.model.bean;

import java.util.List;

public class PersonalStatisticsInfo {

    /**
     * thisMonthEatPercent : 45%
     * eatType : [{"type":"中餐","percent":"70%"},{"type":"西餐","percent":"18%"},{"type":"日残","percent":"12%"}]
     * consumeChart : [{"yaxisNum":["200","354"],"xaxisNum":["1"],"xaxisText":"6月1日","yaxisText":null},{"yaxisNum":["263","125"],"xaxisNum":["2"],"xaxisText":"6月2日","yaxisText":null},{"yaxisNum":["369","854"],"xaxisNum":["3"],"xaxisText":"6月3日","yaxisText":null},{"yaxisNum":["125","365"],"xaxisNum":["4"],"xaxisText":"6月5日","yaxisText":null},{"yaxisNum":["784","254"],"xaxisNum":["5"],"xaxisText":"6月7日","yaxisText":null}]
     * resultCode : 0
     * healthTips : 健康分析:您最近身体不错
     * lastMonthEatPercent : 31%
     * healthAnalysisChart : {"sodium":[{"yaxisNum":"200","xaxisNum":"1","xaxisText":"6月1日","yaxisText":null},{"yaxisNum":"263","xaxisNum":"2","xaxisText":"6月2日","yaxisText":null},{"yaxisNum":"369","xaxisNum":"3","xaxisText":"6月3日","yaxisText":null},{"yaxisNum":"125","xaxisNum":"4","xaxisText":"6月5日","yaxisText":null},{"yaxisNum":"784","xaxisNum":"5","xaxisText":"6月7日","yaxisText":null}],"protein":[{"yaxisNum":"200","xaxisNum":"1","xaxisText":"6月1日","yaxisText":null},{"yaxisNum":"263","xaxisNum":"2","xaxisText":"6月2日","yaxisText":null},{"yaxisNum":"369","xaxisNum":"3","xaxisText":"6月3日","yaxisText":null},{"yaxisNum":"125","xaxisNum":"4","xaxisText":"6月5日","yaxisText":null},{"yaxisNum":"784","xaxisNum":"5","xaxisText":"6月7日","yaxisText":null}],"fat":[{"yaxisNum":"200","xaxisNum":"1","xaxisText":"6月1日","yaxisText":null},{"yaxisNum":"263","xaxisNum":"2","xaxisText":"6月2日","yaxisText":null},{"yaxisNum":"369","xaxisNum":"3","xaxisText":"6月3日","yaxisText":null},{"yaxisNum":"125","xaxisNum":"4","xaxisText":"6月5日","yaxisText":null},{"yaxisNum":"784","xaxisNum":"5","xaxisText":"6月7日","yaxisText":null}],"carbohydrate":[{"yaxisNum":"200","xaxisNum":"1","xaxisText":"6月1日","yaxisText":null},{"yaxisNum":"263","xaxisNum":"2","xaxisText":"6月2日","yaxisText":null},{"yaxisNum":"369","xaxisNum":"3","xaxisText":"6月3日","yaxisText":null},{"yaxisNum":"125","xaxisNum":"4","xaxisText":"6月5日","yaxisText":null},{"yaxisNum":"784","xaxisNum":"5","xaxisText":"6月7日","yaxisText":null}]}
     * consumeTips : 消费分析:上个月花少了
     * eatCount : 20
     * eatAnalysis : 外出就餐频率分析
     */

    private String thisMonthEatPercent;
    private int resultCode;
    private String healthTips;
    private String lastMonthEatPercent;
    private HealthAnalysisChartBean healthAnalysisChart;
    private String consumeTips;
    private String eatCount;
    private String eatAnalysis;
    private List<EatTypeBean> eatType;
    private List<ConsumeChartBean> consumeChart;

    public String getThisMonthEatPercent() {
        return thisMonthEatPercent;
    }

    public void setThisMonthEatPercent(String thisMonthEatPercent) {
        this.thisMonthEatPercent = thisMonthEatPercent;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getHealthTips() {
        return healthTips;
    }

    public void setHealthTips(String healthTips) {
        this.healthTips = healthTips;
    }

    public String getLastMonthEatPercent() {
        return lastMonthEatPercent;
    }

    public void setLastMonthEatPercent(String lastMonthEatPercent) {
        this.lastMonthEatPercent = lastMonthEatPercent;
    }

    public HealthAnalysisChartBean getHealthAnalysisChart() {
        return healthAnalysisChart;
    }

    public void setHealthAnalysisChart(HealthAnalysisChartBean healthAnalysisChart) {
        this.healthAnalysisChart = healthAnalysisChart;
    }

    public String getConsumeTips() {
        return consumeTips;
    }

    public void setConsumeTips(String consumeTips) {
        this.consumeTips = consumeTips;
    }

    public String getEatCount() {
        return eatCount;
    }

    public void setEatCount(String eatCount) {
        this.eatCount = eatCount;
    }

    public String getEatAnalysis() {
        return eatAnalysis;
    }

    public void setEatAnalysis(String eatAnalysis) {
        this.eatAnalysis = eatAnalysis;
    }

    public List<EatTypeBean> getEatType() {
        return eatType;
    }

    public void setEatType(List<EatTypeBean> eatType) {
        this.eatType = eatType;
    }

    public List<ConsumeChartBean> getConsumeChart() {
        return consumeChart;
    }

    public void setConsumeChart(List<ConsumeChartBean> consumeChart) {
        this.consumeChart = consumeChart;
    }

    public static class HealthAnalysisChartBean {
        private List<HealthBean> sodium;
        private List<HealthBean> protein;
        private List<HealthBean> fat;
        private List<HealthBean> carbohydrate;

        public List<HealthBean> getSodium() {
            return sodium;
        }

        public void setSodium(List<HealthBean> sodium) {
            this.sodium = sodium;
        }

        public List<HealthBean> getProtein() {
            return protein;
        }

        public void setProtein(List<HealthBean> protein) {
            this.protein = protein;
        }

        public List<HealthBean> getFat() {
            return fat;
        }

        public void setFat(List<HealthBean> fat) {
            this.fat = fat;
        }

        public List<HealthBean> getCarbohydrate() {
            return carbohydrate;
        }

        public void setCarbohydrate(List<HealthBean> carbohydrate) {
            this.carbohydrate = carbohydrate;
        }

        public static class HealthBean {
            /**
             * yaxisNum : 200
             * xaxisNum : 1
             * xaxisText : 6月1日
             * yaxisText : null
             */

            private String yaxisNum;
            private String xaxisNum;
            private String xaxisText;
            private Object yaxisText;

            public String getYaxisNum() {
                return yaxisNum;
            }

            public void setYaxisNum(String yaxisNum) {
                this.yaxisNum = yaxisNum;
            }

            public String getXaxisNum() {
                return xaxisNum;
            }

            public void setXaxisNum(String xaxisNum) {
                this.xaxisNum = xaxisNum;
            }

            public String getXaxisText() {
                return xaxisText;
            }

            public void setXaxisText(String xaxisText) {
                this.xaxisText = xaxisText;
            }

            public Object getYaxisText() {
                return yaxisText;
            }

            public void setYaxisText(Object yaxisText) {
                this.yaxisText = yaxisText;
            }
        }

    }

    public static class EatTypeBean {
        /**
         * type : 中餐
         * percent : 70%
         */

        private String type;
        private String percent;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }
    }

    public static class ConsumeChartBean {
        /**
         * yaxisNum : ["200","354"]
         * xaxisNum : ["1"]
         * xaxisText : 6月1日
         * yaxisText : null
         */

        private String xaxisText;
        private Object yaxisText;
        private List<String> yaxisNum;
        private List<String> xaxisNum;

        public String getXaxisText() {
            return xaxisText;
        }

        public void setXaxisText(String xaxisText) {
            this.xaxisText = xaxisText;
        }

        public Object getYaxisText() {
            return yaxisText;
        }

        public void setYaxisText(Object yaxisText) {
            this.yaxisText = yaxisText;
        }

        public List<String> getYaxisNum() {
            return yaxisNum;
        }

        public void setYaxisNum(List<String> yaxisNum) {
            this.yaxisNum = yaxisNum;
        }

        public List<String> getXaxisNum() {
            return xaxisNum;
        }

        public void setXaxisNum(List<String> xaxisNum) {
            this.xaxisNum = xaxisNum;
        }
    }
}
