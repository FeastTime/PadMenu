/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.presenter.statistics;

import com.feasttime.model.RetrofitService;
import com.feasttime.model.bean.HealthIndexAssessmentInfo;
import com.feasttime.model.bean.PersonalStatisticsInfo;
import com.feasttime.tools.LogUtil;

import java.util.HashMap;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class StatisticsPresenter implements StatisticsContract.IStatisticsPresenter{

    private StatisticsContract.IStatisticsView mStatisticsView;
    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void init(StatisticsContract.IStatisticsView view) {
        mStatisticsView = view;
    }

    @Override
    public void getStatisticsPersonalInfo(String token) {
        RetrofitService.getPersonalStatistics(token).subscribe(new Consumer<PersonalStatisticsInfo>() {
            @Override
            public void accept(PersonalStatisticsInfo statisticsPersonInfo) throws Exception {
                //mIMenuView.showMenu(menuItemInfo);
//                iOrderView.showMyOrder(orderInfo.getMyOrderList());
//                iOrderView.showRecommendOrder(orderInfo.getRecommendOrderList());
                mStatisticsView.showData(statisticsPersonInfo);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //这里接收onError
                LogUtil.d("result","error:");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                //这里接收onComplete。
                LogUtil.d("result","complete");
            }
        });
    }

    @Override
    public void getgetHealthIndexAssessment(String orderID) {
        HashMap<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("orderID", orderID);
        RetrofitService.getHealthIndexAssessment(infoMap).subscribe(new Consumer<HealthIndexAssessmentInfo>() {
            @Override
            public void accept(HealthIndexAssessmentInfo healthIndexAssessmentInfo) throws Exception {
                //mIMenuView.showMenu(menuItemInfo);
//                iOrderView.showMyOrder(orderInfo.getMyOrderList());
//                iOrderView.showRecommendOrder(orderInfo.getRecommendOrderList());
                mStatisticsView.showHealthIndexAssessment(healthIndexAssessmentInfo);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //这里接收onError
                LogUtil.d("result","error:");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                //这里接收onComplete。
                LogUtil.d("result","complete");
            }
        });
    }
}
