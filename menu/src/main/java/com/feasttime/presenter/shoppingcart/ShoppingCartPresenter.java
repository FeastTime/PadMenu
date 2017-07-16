package com.feasttime.presenter.shoppingcart;

import com.feasttime.model.RetrofitService;
import com.feasttime.model.bean.CreateOrderInfo;
import com.feasttime.model.bean.OrderInfo;
import com.feasttime.model.bean.ShoppingCartInfo;
import com.feasttime.presenter.menu.MenuContract;
import com.feasttime.tools.LogUtil;
import com.feasttime.tools.PreferenceUtil;

import java.util.HashMap;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2017/4/19.
 */

public class ShoppingCartPresenter implements ShoppingCartContract.IShoppingCartPresenter{
    private ShoppingCartContract.IShoppingCartView mIShoppingCartView;



    @Override
    public void addShoppingCart(String ID,String orderID) {
        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("orderID",orderID);
        infoMap.put("ID",ID);
        RetrofitService.addShoppingCart(infoMap).subscribe(new Consumer<OrderInfo>(){
            @Override
            public void accept(OrderInfo orderInfo) throws Exception {
                LogUtil.d("result","aa");
                mIShoppingCartView.addShoppingCartComplete(orderInfo);
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
    public void removeShoppingCart(String ID,String orderID) {
        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("orderID",orderID);
        infoMap.put("ID",ID);
        RetrofitService.removeShoppingCart(infoMap).subscribe(new Consumer<OrderInfo>(){
            @Override
            public void accept(OrderInfo orderInfo) throws Exception {
                LogUtil.d("result","aa");
                mIShoppingCartView.removeShoppingCartComplete(orderInfo);
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
    public void getShoppingCartList(String orderID) {
        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("orderID",orderID);
        RetrofitService.getShoppingCartList(infoMap).subscribe(new Consumer<OrderInfo>() {
            @Override
            public void accept(OrderInfo orderInfo) throws Exception {
                //mIMenuView.showMenu(menuItemInfo);
//                mIShoppingCartView.showOrderList(orderInfo.getMyOrderList());
//                mIShoppingCartView.showRecommendList(orderInfo.getRecommendOrderList());
                mIShoppingCartView.getShoppingcartListComplete(orderInfo);
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
    public void init(ShoppingCartContract.IShoppingCartView view) {
        this.mIShoppingCartView = view;
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
