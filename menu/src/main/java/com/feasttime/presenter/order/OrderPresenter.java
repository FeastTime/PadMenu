package com.feasttime.presenter.order;

import com.feasttime.model.RetrofitService;
import com.feasttime.model.bean.CreateOrderInfo;
import com.feasttime.model.bean.MenuInfo;
import com.feasttime.model.bean.MenuItemInfo;
import com.feasttime.model.bean.OrderInfo;
import com.feasttime.model.bean.PayOrderInfo;
import com.feasttime.model.bean.PlaceOrderInfo;
import com.feasttime.tools.LogUtil;
import com.feasttime.tools.PreferenceUtil;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by chen on 2017/5/11.
 */

public class OrderPresenter implements OrderContract.IOrderPresenter {
    private OrderContract.IOrderView iOrderView;

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
    public void init(OrderContract.IOrderView view) {
         this.iOrderView = view;
    }

    @Override
    public void createOrder(String token) {
        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("token",token);
        RetrofitService.createOrder(infoMap).subscribe(new Consumer<CreateOrderInfo>(){
            @Override
            public void accept(CreateOrderInfo createOrderInfo) throws Exception {
                LogUtil.d("result","aa");
                PreferenceUtil.setStringKey("orderID",createOrderInfo.getOrderID());
                iOrderView.createOrderComplete();
//                iOrderView.showMyOrder();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //这里接收onError
                LogUtil.d("result","error");
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
    public void payOrder(String orderID) {
        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("orderID",orderID);
        RetrofitService.payOrder(infoMap).subscribe(new Consumer<PayOrderInfo>(){
            @Override
            public void accept(PayOrderInfo payOrderInfo) throws Exception {
                LogUtil.d("result","aa");

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //这里接收onError
                LogUtil.d("result","error");
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
    public void placeOrder(String orderID) {
        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("orderID",orderID);
        RetrofitService.placeOrder(infoMap).subscribe(new Consumer<PlaceOrderInfo>(){
            @Override
            public void accept(PlaceOrderInfo placeOrderInfo) throws Exception {
                LogUtil.d("result","aa");
                iOrderView.placeOrderComplete();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //这里接收onError
                LogUtil.d("result","error");
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
