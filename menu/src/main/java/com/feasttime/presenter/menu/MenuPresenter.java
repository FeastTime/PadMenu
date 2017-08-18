package com.feasttime.presenter.menu;


import com.feasttime.model.RetrofitService;
import com.feasttime.model.bean.DishesCategoryInfo;
import com.feasttime.model.bean.MenuInfo;
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
 * 获取天气的Presenter
 * Created by glh on 2016-06-23.
 */
public class MenuPresenter implements MenuContract.IMenuPresenter {

    private MenuContract.IMenuView mIMenuView;

    @Override
    public void init(MenuContract.IMenuView view) {
        this.mIMenuView = view;
    }

    @Override
    public void getMenu(String token, String orderID, String classType, String page) {
        HashMap<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("token", token);
        infoMap.put("orderID", orderID);
        infoMap.put("classType", classType);
        infoMap.put("page", page);

        RetrofitService.getMenu(infoMap).subscribe(new Consumer<MenuInfo>() {
            @Override
            public void accept(MenuInfo menuInfo) throws Exception {
                LogUtil.d("result", "aa");
                mIMenuView.showMenu(menuInfo);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //这里接收onError
                //throwable.printStackTrace();
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
    public void getDishesCategory() {
        HashMap<String, Object> infoMap = new HashMap<String, Object>();
        String storeId = PreferenceUtil.getStringKey(PreferenceUtil.STORE_ID);
        String token = PreferenceUtil.getStringKey("token");
        infoMap.put("storeId", storeId);
        infoMap.put("token",token);
        RetrofitService.getDishesCategoryList(infoMap).map(new Function<DishesCategoryInfo, List<DishesCategoryInfo.DishesCategoryListBean>>() {
            @Override
            public List<DishesCategoryInfo.DishesCategoryListBean> apply(DishesCategoryInfo dishesCategoryInfo) throws Exception {
                return dishesCategoryInfo.getDishesCategoryList();
            }
        }).flatMap(new Function<List<DishesCategoryInfo.DishesCategoryListBean>, ObservableSource<DishesCategoryInfo.DishesCategoryListBean>>() {
            @Override
            public ObservableSource<DishesCategoryInfo.DishesCategoryListBean> apply(List<DishesCategoryInfo.DishesCategoryListBean> dishesCategoryListBeen) throws Exception {
                return Observable.fromIterable(dishesCategoryListBeen);
            }
        }).subscribe(new Consumer<DishesCategoryInfo.DishesCategoryListBean>() {
            @Override
            public void accept(DishesCategoryInfo.DishesCategoryListBean dishesCategoryListBean) throws Exception {
                mIMenuView.showDishesCategory(dishesCategoryListBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //这里接收onError
                LogUtil.d("result", "error:");
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
