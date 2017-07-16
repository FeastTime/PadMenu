/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.presenter.statistics;

import com.feasttime.model.bean.PersonalStatisticsInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.IBaseView;

public interface StatisticsContract {
    interface IStatisticsView extends IBaseView {
        void showData(PersonalStatisticsInfo result);
    }

    interface IStatisticsPresenter extends IBasePresenter<IStatisticsView> {
        void getStatisticsPersonalInfo(String token);
    }
}
