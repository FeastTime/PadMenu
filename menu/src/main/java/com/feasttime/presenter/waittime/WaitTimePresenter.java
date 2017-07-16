package com.feasttime.presenter.waittime;

import com.feasttime.model.RetrofitService;
import com.feasttime.model.bean.WaitTimeAdInfo;
import com.feasttime.model.bean.WaitTimeMenuInfo;
import com.feasttime.model.bean.WaitTimeMenuItemInfo;
import com.feasttime.tools.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class WaitTimePresenter implements WaitTimeContract.IWaitTimePresenter {

    private WaitTimeContract.IWaitTimeView mIWaitTimeView;

    @Override
    public void getWaitTimeAD(String token, int width ,int height, int number) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                ,"{\"width\":\""+243+"\",\"height\":\""+146+"\",\"token\":\""+token+"\",\"num\":\""+number+"\"}");

        RetrofitService
                .getWaitTimeAdList(requestBody)
                .subscribe(
                        new Consumer<WaitTimeAdInfo>() {

                            @Override
                            public void accept(WaitTimeAdInfo waitTimeAdInfo) throws Exception {

//                                if (null == responseJson && responseJson.length() == 0)
//                                    return ;
//
//                                WaitTimeAdInfo waitTimeAdInfo = JSON.parseObject(responseJson, WaitTimeAdInfo.class);
                                mIWaitTimeView.showWaitTimeAD(waitTimeAdInfo);
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
    public void getWaitTimeMenuList(String token, String orderId) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                ,"{\"width\":\"243\",\"height\":\"146\",\"token\":\"123456\",\"num\":\"10\"}");

        RetrofitService
                .getWaitTimeMenuList(requestBody)
                .subscribe(
                        new Consumer<WaitTimeMenuInfo>() {

                            @Override
                            public void accept(WaitTimeMenuInfo waitTimeMenuInfo) throws Exception {


                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                //这里接收onError
                                LogUtil.d("result", "error");

                                WaitTimeMenuInfo waitTimeMenuInfo = new WaitTimeMenuInfo();

                                waitTimeMenuInfo.setNeedTime(50*60*1000);

                                List<WaitTimeMenuItemInfo> list = new ArrayList<>();
                                WaitTimeMenuItemInfo waitTimeMenuItemInfo;

                                for (int i = 0; i < 24; i++) {

                                    waitTimeMenuItemInfo = new WaitTimeMenuItemInfo();
                                    waitTimeMenuItemInfo.setDishName("土豆丝" + i);
                                    waitTimeMenuItemInfo.setNeedTime(2*60*1000);
                                    // 10:55
                                    waitTimeMenuItemInfo.setStartTime(1500123817425L + 7*1000*60 -1000*10*i);
                                    waitTimeMenuItemInfo.setState((byte) (i%3));

                                    list.add(waitTimeMenuItemInfo);
                                }

                                waitTimeMenuInfo.setList(list);
                                waitTimeMenuInfo.setNeedTime(30 * 60 * 1000);
                                waitTimeMenuInfo.setOrderTime(0);
                                waitTimeMenuInfo.setState((byte) 0);

                                mIWaitTimeView.showWaitTimeDishList(waitTimeMenuInfo);
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
    public void init(WaitTimeContract.IWaitTimeView view) {
        this.mIWaitTimeView = view;
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
