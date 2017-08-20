package com.feasttime.presenter.selentad;

import android.util.Log;

import com.feasttime.model.RetrofitService;
import com.feasttime.model.bean.SilentAd;
import com.feasttime.tools.LogUtil;

import java.util.HashMap;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


public class SilentPresenter implements SilentAdContract.ISilentPresenter {

    private SilentAdContract.ISilentView iSilentView;

    @Override
    public void getSilentADUrl(int width, int height, int nu, String type) {


        HashMap<String, Object> map = new HashMap<>();
        map.put("width", width);
        map.put("height", height);
        map.put("nu", nu);
        map.put("type", type);

        RetrofitService
                .getSilentAD(map)
                .subscribe(
                        new Consumer<SilentAd>() {

                            @Override
                            public void accept(SilentAd silentAd) throws Exception {

                                Log.d("test", silentAd.getID());
                                iSilentView.getSilentADUrlComplete(silentAd);
                            }

                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                //这里接收onError
                                LogUtil.d("result", "error" + throwable.getMessage());

                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                //这里接收onComplete。
                                LogUtil.d("result", "complete");
                            }
                        });
    }

    @Override
    public void init(SilentAdContract.ISilentView view) {
        this.iSilentView = view;
    }


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
}
