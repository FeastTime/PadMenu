/*
 * Copyright (c) 2017. sheng yan
 */

/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.feasttime.adapter.HorizontalListViewAdapter;
import com.feasttime.adapter.MainMenuPagerAdapter;
import com.feasttime.listener.OrderModifyListener;
import com.feasttime.menu.R;
import com.feasttime.model.bean.DishesCategoryInfo;
import com.feasttime.model.bean.MenuInfo;
import com.feasttime.model.bean.MenuItemInfo;
import com.feasttime.model.bean.MyOrderListItemInfo;
import com.feasttime.model.bean.OrderInfo;
import com.feasttime.model.bean.RecommendOrderListItemInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.menu.MenuContract;
import com.feasttime.presenter.menu.MenuPresenter;
import com.feasttime.presenter.order.OrderContract;
import com.feasttime.presenter.shoppingcart.ShoppingCartContract;
import com.feasttime.presenter.shoppingcart.ShoppingCartPresenter;
import com.feasttime.tools.LogUtil;
import com.feasttime.tools.PreferenceUtil;
import com.feasttime.tools.ScreenTools;
import com.feasttime.view.MainActivity;
import com.feasttime.view.SilentADActivity;
import com.feasttime.widget.HorizontalListView;
import com.feasttime.widget.jazzyviewpager.JazzyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainMenuFragment extends BaseFragment implements MenuContract.IMenuView,ShoppingCartContract.IShoppingCartView, View.OnClickListener,ViewPager.OnPageChangeListener,MainMenuPagerAdapter.OnItemClick,OrderContract.IOrderView,OrderModifyListener {
    private final String TAG = "MainMenuFragment";
    private final static int PAGE_COUNT = 3;  //这个参数需要服务器传回
    private final static int PER_PAGE_ITEM = 3; //这个参数需要服务器传回
    private final static int ALL_SIZE = 9; //这个参数需要服务器传回


    private ShoppingCartPresenter mShoppingCartPresenter = new ShoppingCartPresenter();
    private MenuPresenter mMenuPresenter = new MenuPresenter();


    @Bind(R.id.main_menu_viewpager)
    JazzyViewPager jazzyViewPager;

    @Bind(R.id.main_activity_left_ib)
    ImageButton leftIb;

    @Bind(R.id.main_activity_right_ib)
    ImageButton rightIb;

    @Bind(R.id.toTheAdBtn)
    Button mToAdBtn;

    @Bind(R.id.main_activity_recommend_lv)
    HorizontalListView recommendLv;


    MainMenuPagerAdapter mainMenuPagerAdapter;

    private Context mContext;
    private String currentClassType;
    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[]{mMenuPresenter,mShoppingCartPresenter};
    }

    @Override
    protected void onInitPresenters() {
        mMenuPresenter.init(this);
        mShoppingCartPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.main_menu_fragment;
    }

    @Override
    protected void initViews() {
        jazzyViewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.Tablet);
        jazzyViewPager.setPageMargin(30);

        test();
    }

    public void clearAllData() {
        jazzyViewPager.setCurrentItem(0);  //选择回第一页
        if (mainMenuPagerAdapter != null) {
            mainMenuPagerAdapter.clearAllData();
        }
    }


    private void test() {
        String titles[] = {"a","b","c","d","b","c","d","b","c","d","b","c","d","b","c","d","b","c","d","b","c","d","b","c","d"};
        HorizontalListViewAdapter horizontalListViewAdapter = new HorizontalListViewAdapter(this.getActivity(),titles);
        recommendLv.setAdapter(horizontalListViewAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = inflater.getContext();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void showDishesCategory(DishesCategoryInfo.DishesCategoryListBean dishesCategoryListBean) {

    }

    @Override
    public void showMenu(MenuInfo menuInfo) {


        if (mainMenuPagerAdapter == null) {
            mainMenuPagerAdapter = new MainMenuPagerAdapter(mContext,jazzyViewPager,menuInfo);
            mainMenuPagerAdapter.setOnItemClickListener(this);
            jazzyViewPager.setAdapter(mainMenuPagerAdapter);
            jazzyViewPager.setOnPageChangeListener(this);
        } else {
            if (menuInfo.getDishesList() == null) {
                mainMenuPagerAdapter.setList(new ArrayList<MenuItemInfo>());
            } else if (menuInfo.getDishesList().size() == 0) {
                mainMenuPagerAdapter.setList(new ArrayList<MenuItemInfo>());
            } else {
                mainMenuPagerAdapter.appendData(menuInfo.getDishesList());
            }
        }
    }


    @OnClick({R.id.main_activity_left_ib, R.id.main_activity_right_ib, R.id.toTheAdBtn})
    @Override
    public void onClick(View v) {
        if (v == leftIb) {
            jazzyViewPager.setCurrentItem(jazzyViewPager.getCurrentItem() - 1);
        } else if (v == rightIb) {
            jazzyViewPager.setCurrentItem(jazzyViewPager.getCurrentItem() + 1);
        } else if (v == mToAdBtn) {
            startActivity(new Intent(mContext,SilentADActivity.class));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        LogUtil.d(TAG,"the page selected:" + position);
        //从第二页才开始加载,第一页由网络请求返回,如果当前页有数据则不加载
        if (position > 0 && !mainMenuPagerAdapter.checkExistData(position)) {
            String token = PreferenceUtil.getStringKey("token");
            String orderID = PreferenceUtil.getStringKey("orderID");
            String storeId = PreferenceUtil.getStringKey(PreferenceUtil.STORE_ID);
            mMenuPresenter.getMenu(token,storeId,currentClassType,String.valueOf(position));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDishesPicClicked(MenuItemInfo menuItemInfo,float x,float y) {
        ((MainActivity)this.getActivity()).refreshCartAnimation(new int[] {(int)x,(int)y});
        mShoppingCartPresenter.addShoppingCart(menuItemInfo);
    }

    @Override
    public void addShoppingCartComplete(OrderInfo orderInfo) {

    }

    @Override
    public void removeShoppingCartComplete(OrderInfo orderInfo) {

    }

    @Override
    public void getShoppingcartListComplete(OrderInfo orderInfo) {

    }


    @Override
    public void createOrderComplete() {

    }

    @Override
    public void showRecommendList(List<RecommendOrderListItemInfo> recommendOrderList) {

    }

    @Override
    public void showOrderList(List<MyOrderListItemInfo> myOrderList) {

    }

    @Override
    public void placeOrderComplete() {

    }

    @Override
    public void payOrderComplete() {

    }

    public void showContentMenu(String token,String storeId,String menuFlag) {
        currentClassType = menuFlag;
        mMenuPresenter.getMenu(token,storeId,menuFlag,"0");

    }

    @Override
    public void onAddClicked(String ID) {

    }

    @Override
    public void onReduceClicked(String ID) {

    }
}
