package com.feasttime.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.feasttime.adapter.HorizontalListViewAdapter;
import com.feasttime.fragment.MainMenuFragment;
import com.feasttime.fragment.MyOrderFragment;
import com.feasttime.fragment.RecommendMenuFragment;
import com.feasttime.menu.R;
import com.feasttime.model.bean.DishesCategoryInfo;
import com.feasttime.model.bean.MenuInfo;
import com.feasttime.model.bean.MenuItemInfo;
import com.feasttime.model.bean.MyOrderListItemInfo;
import com.feasttime.model.bean.OrderInfo;
import com.feasttime.model.bean.RecommendOrderListItemInfo;
import com.feasttime.model.bean.ScreenInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.menu.MenuContract;
import com.feasttime.presenter.menu.MenuPresenter;
import com.feasttime.presenter.order.OrderContract;
import com.feasttime.presenter.order.OrderPresenter;
import com.feasttime.presenter.shoppingcart.ShoppingCartContract;
import com.feasttime.presenter.shoppingcart.ShoppingCartPresenter;
import com.feasttime.tools.DeviceTool;
import com.feasttime.tools.LogUtil;
import com.feasttime.tools.PreferenceUtil;
import com.feasttime.tools.ScreenTools;
import com.feasttime.tools.UtilTools;
import com.feasttime.widget.HorizontalListView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MenuContract.IMenuView,ShoppingCartContract.IShoppingCartView, View.OnClickListener,OrderContract.IOrderView {
    private static final String TAG = "MainActivity";
    private ShoppingCartPresenter mShoppingCartPresenter = new ShoppingCartPresenter();
    private MenuPresenter mMenuPresenter = new MenuPresenter();
    private OrderPresenter mOrderPresenter = new OrderPresenter();

    @Bind(R.id.main_fragment_container_fl)
    FrameLayout fragmentContainerFl;

    @Bind(R.id.title_bar_cart_ib)
    ImageButton cartIb;

    @Bind(R.id.title_bar_layout_menu_ib)
    ImageButton menuIb;

    @Bind(R.id.title_bar_content_rb)
    RadioGroup mTtitleBarMenuRb;

    @Bind(R.id.title_bar_layout_login_tv)
    TextView loginTv;

    @Bind(R.id.main_activity_recommend_lv)
    HorizontalListView recommendLv;

    private MyOrderFragment myOrderFragment;
    private MainMenuFragment mainMenuFragment;
    private RecommendMenuFragment recommendMenuFragment;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenInfo info = DeviceTool.getDeviceScreenInfo(this);

        LogUtil.d(TAG,info.getWidth() + "X" + info.getHeight());

//        startActivity(new Intent(this,TestActivity.class));


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTtitleBarMenuRb.getChildCount() == 0) {
            //没有请求过才去请求
            mMenuPresenter.getDishesCategory();
        }

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            testPopwindow();
        }
    }

    private void testPopwindow() {
        TextView tv = new TextView(this);
        tv.setText("nihao\na\nb\nc\nd\ne\nf\nh\nj\nl\nc");
        tv.setTextSize(30);
        tv.setBackgroundColor(Color.GREEN);

        PopupWindow mPopupWindow=new PopupWindow(tv, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mPopupWindow.setFocusable(false);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.showAtLocation(findViewById(R.id.main_activity_title_bar), Gravity.CENTER,60,60);
    }

    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[]{mShoppingCartPresenter,mMenuPresenter,mOrderPresenter};
    }

    @Override
    protected void onInitPresenters() {
        mShoppingCartPresenter.init(this);
        mMenuPresenter.init(this);
        mOrderPresenter.init(this);
    }

    @Override
    protected void initViews() {
        mainMenuFragment = new MainMenuFragment();
        recommendMenuFragment = new RecommendMenuFragment();
        myOrderFragment = new MyOrderFragment();

        FragmentManager fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_container_fl,mainMenuFragment,"main_menu");
        fragmentTransaction.add(R.id.main_fragment_container_fl,recommendMenuFragment,"recommend_menu");
        fragmentTransaction.add(R.id.main_fragment_container_fl,myOrderFragment,"my_order");
        fragmentTransaction.show(mainMenuFragment);
        fragmentTransaction.hide(recommendMenuFragment);
        fragmentTransaction.hide(myOrderFragment);
        fragmentTransaction.commit();

        test();
    }


    private void test() {
        String titles[] = {"a","b","c","d","b","c","d","b","c","d","b","c","d","b","c","d","b","c","d","b","c","d","b","c","d"};
        HorizontalListViewAdapter horizontalListViewAdapter = new HorizontalListViewAdapter(this,titles);
        recommendLv.setAdapter(horizontalListViewAdapter);
    }



    @Override
    protected int getLayoutResId() {
        return R.layout.main_activity;
    }


    @Override
    public void showMenu(final MenuInfo menuItemInfo) {
        LogUtil.d(TAG,"menuInfo complete");
    }

    public void jumpToRecommend(MenuItemInfo menuItemInfo){
        recommendMenuFragment.setMenuData(menuItemInfo);
        getFragmentManager().beginTransaction().show(recommendMenuFragment).hide(mainMenuFragment).hide(myOrderFragment).commit();
    }

    @Override
    public void createOrderComplete() {

    }

    @OnClick({R.id.title_bar_cart_ib, R.id.title_bar_layout_menu_ib, R.id.title_bar_layout_login_tv})
    @Override
    public void onClick(View v) {
        if (v == cartIb) {
            getFragmentManager().beginTransaction().show(myOrderFragment).hide(mainMenuFragment).hide(recommendMenuFragment).commit();
            myOrderFragment.showCart();
        } else if (v == menuIb) {
            getFragmentManager().beginTransaction().show(mainMenuFragment).hide(myOrderFragment).hide(recommendMenuFragment).commit();
        } else if (loginTv == v) {
            startActivity(new Intent(v.getContext(),LoginActivity.class));
        }
    }

    public void showMainMenu() {
        getFragmentManager().beginTransaction().show(mainMenuFragment).hide(myOrderFragment).hide(recommendMenuFragment).commit();
    }

    @Override
    public void showDishesCategory(DishesCategoryInfo.DishesCategoryListBean dishesCategoryListBean) {
        RadioButton menuRb = new RadioButton(this);
        menuRb.setButtonDrawable(android.R.color.transparent);
        menuRb.setGravity(Gravity.CENTER);
        menuRb.setText(UtilTools.decodeStr(dishesCategoryListBean.getCategoryName()) + "\n" + "hot");
        menuRb.setTextColor(Color.WHITE);
        menuRb.setTag(dishesCategoryListBean.getCategoryId());
        menuRb.setPadding(ScreenTools.dip2px(this,40),0, ScreenTools.dip2px(this,40),0);
        if (mTtitleBarMenuRb.getChildCount() == 0) {
            menuRb.setBackgroundResource(R.drawable.title_left_menu_selector);
        } else {
            menuRb.setBackgroundResource(R.drawable.title_normal_menu_selector);
        }

        menuRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mainMenuFragment.clearAllData();
                    String classType = buttonView.getTag().toString();
                    String token = PreferenceUtil.getStringKey("token");
                    String orderID = PreferenceUtil.getStringKey("orderID");
                    mainMenuFragment.showContentMenu(token,orderID,classType);
                } else {
                }
            }
        });

        mTtitleBarMenuRb.addView(menuRb);
        ViewGroup.LayoutParams params = menuRb.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        menuRb.setLayoutParams(params);
        RadioButton firstRb = ((RadioButton)mTtitleBarMenuRb.getChildAt(0));
        if (!firstRb.isChecked()) {
            firstRb.setChecked(true);
        }
    }



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK && !mainMenuFragment.isVisible()) {
            getFragmentManager().beginTransaction().show(mainMenuFragment).hide(myOrderFragment).hide(recommendMenuFragment).commit();
            return true;
        }
        return super.onKeyUp(keyCode, event);
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
    public void payOrderComplete() {

    }

    @Override
    public void placeOrderComplete() {

    }

    @Override
    public void showRecommendList(List<RecommendOrderListItemInfo> recommendOrderList) {

    }

    @Override
    public void showOrderList(List<MyOrderListItemInfo> myOrderList) {

    }
}
