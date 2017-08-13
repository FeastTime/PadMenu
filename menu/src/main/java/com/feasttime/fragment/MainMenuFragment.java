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

import com.feasttime.adapter.MainMenuPagerAdapter;
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
import com.feasttime.tools.UtilTools;
import com.feasttime.view.MainActivity;
import com.feasttime.view.SilentADActivity;
import com.feasttime.widget.jazzyviewpager.JazzyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainMenuFragment extends BaseFragment implements MenuContract.IMenuView,ShoppingCartContract.IShoppingCartView, View.OnClickListener,ViewPager.OnPageChangeListener,MainMenuPagerAdapter.OnItemClick,OrderContract.IOrderView{
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

    @Bind(R.id.menu_item_layout_viewpage_indicate_rg)
    RadioGroup viewpageIndicateRg;

    @Bind(R.id.toTheAdBtn)
    Button mToAdBtn;

    MainMenuPagerAdapter mainMenuPagerAdapter;

    private Context mContext;
    private String currentClassType;
    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[]{mMenuPresenter};
    }

    @Override
    protected void onInitPresenters() {
        mMenuPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.main_menu_fragment;
    }

    @Override
    protected void initViews() {
        jazzyViewPager.setTransitionEffect(JazzyViewPager.TransitionEffect.Tablet);
        jazzyViewPager.setPageMargin(30);
    }

    public void clearAllData() {
        jazzyViewPager.setCurrentItem(0);  //选择回第一页
        if (mainMenuPagerAdapter != null) {
            mainMenuPagerAdapter.clearAllData();
        }
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
    public void showMenu(MenuInfo result) {


        if (mainMenuPagerAdapter == null) {
            mainMenuPagerAdapter = new MainMenuPagerAdapter(mContext,jazzyViewPager,result.getDishesList());
            mainMenuPagerAdapter.setOnItemClickListener(this);
            jazzyViewPager.setAdapter(mainMenuPagerAdapter);
            jazzyViewPager.setOnPageChangeListener(this);
        } else {
            if (result.getDishesList() == null) {
                mainMenuPagerAdapter.setList(new ArrayList<MenuItemInfo>());
            } else if (result.getDishesList().size() == 0) {
                mainMenuPagerAdapter.setList(new ArrayList<MenuItemInfo>());
            } else {
                mainMenuPagerAdapter.appendData(result.getDishesList());
            }
        }

        if (viewpageIndicateRg.getChildCount() < PAGE_COUNT) {
            //只有切换标签才能满足条件
            viewpageIndicateRg.removeAllViews();

            //没有加过圆点才加
            for (int i = 0 ; i < PAGE_COUNT ; i++) {
                RadioButton rb = new RadioButton(mContext);
                rb.setBackgroundResource(R.drawable.viewpage_indicate_selector);
                rb.setButtonDrawable(android.R.color.transparent);
                rb.setWidth(ScreenTools.dip2px(mContext,10));
                rb.setHeight(ScreenTools.dip2px(mContext,10));
                viewpageIndicateRg.addView(rb);
                rb.setTag(i);
                rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        LogUtil.d("result","the position ->" + buttonView.getTag());
                    }
                });
            }
            ((RadioButton)viewpageIndicateRg.getChildAt(0)).setChecked(true);
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
        Object radioBtnObj = viewpageIndicateRg.getChildAt(position);
        if (radioBtnObj != null) {
            ((RadioButton)radioBtnObj).setChecked(true);
        }

        //从第二页才开始加载,第一页由网络请求返回,如果当前页有数据则不加载
        if (position > 0 && !mainMenuPagerAdapter.checkExistData(position)) {
            String token = PreferenceUtil.getStringKey("token");
            String orderID = PreferenceUtil.getStringKey("orderID");
            mMenuPresenter.getMenu(token,orderID,currentClassType,String.valueOf(position + 1));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDishesPicClicked(MenuItemInfo menuItemInfo,float x,float y) {
//        ((MainActivity)this.getActivity()).jumpToRecommend(menuItemInfo);
        UtilTools.addOneDishes(this.getActivity(),new int[] {(int)x,(int)y},null);
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

    public void showContentMenu(String token,String orderID,String menuFlag) {
        currentClassType = menuFlag;
        mMenuPresenter.getMenu(token,orderID,menuFlag,"1");

    }
}
