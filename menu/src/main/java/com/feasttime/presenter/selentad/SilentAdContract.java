package com.feasttime.presenter.selentad;

import com.feasttime.model.bean.SilentAd;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.IBaseView;


public interface SilentAdContract {

    interface ISilentView extends IBaseView {

        void getSilentADUrlComplete(SilentAd silentAd);

    }

    interface ISilentPresenter extends IBasePresenter<ISilentView> {
        void getSilentADUrl(int width, int height, int nu, String type);
    }

}
