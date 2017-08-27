package com.feasttime.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.feasttime.fragment.MainMenuFragment;
import com.feasttime.fragment.MyOrderFragment;
import com.feasttime.fragment.RecommendMenuFragment;
import com.feasttime.menu.R;
import com.feasttime.model.CachedData;
import com.feasttime.model.bean.DishesCategoryInfo;
import com.feasttime.model.bean.IngredientsMenuInfo;
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
import com.feasttime.tools.RxBus;
import com.feasttime.tools.UtilTools;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

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

    @Bind(R.id.title_bar_content_ll)
    LinearLayout mTtitleBarMenuLl;

    @Bind(R.id.title_bar_layout_staff_entry_tv)
    TextView loginTv;

    @Bind(R.id.title_bar_cart_num_tv)
    TextView titleBarCarNumTv;

    private int cartLocation[];
    private MyOrderFragment myOrderFragment;
    private MainMenuFragment mainMenuFragment;
    private RecommendMenuFragment recommendMenuFragment;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenInfo info = DeviceTool.getDeviceScreenInfo(this);
        LogUtil.d(TAG,"screen size:" + info.getWidth() + "X" + info.getHeight());

//        startActivity(new Intent(this,TestActivity.class));


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerRxbus();
        refreshBadge();
        if (mTtitleBarMenuLl.getChildCount() == 0) {
            //没有请求过才去请求
            mMenuPresenter.getDishesCategory();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        RxBus.getInstance().unregisterAll();
    }

    private void registerRxbus() {
        Flowable<String> f = RxBus.getInstance().register(String.class);
        f.subscribe(new Consumer<String>() {
            @Override
            public void accept(String integer) throws Exception {
                refreshBadge();
            }
        });
    }

    private void refreshBadge() {
        if (CachedData.orderInfo == null) {
            return;
        }

        List<MyOrderListItemInfo> myOrderList = CachedData.orderInfo.getMyOrderList();
        int count = myOrderList.size();
        int countOrders = 0;
        for (int i = 0 ; i < count ; i++) {
            MyOrderListItemInfo myOrderListItemInfo = myOrderList.get(i);
            countOrders = countOrders + Integer.parseInt(myOrderListItemInfo.getAmount());
        }

        titleBarCarNumTv.setText(countOrders + "");
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

    @OnClick({R.id.title_bar_cart_ib, R.id.title_bar_layout_menu_ib, R.id.title_bar_layout_staff_entry_tv})
    @Override
    public void onClick(View v) {
        if (v == cartIb) {
//            getFragmentManager().beginTransaction().show(myOrderFragment).hide(mainMenuFragment).hide(recommendMenuFragment).commit();
//            myOrderFragment.showCart();
            startActivity(new Intent(this,ShoppingCartActivity.class));
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
    public void showDishesCategory(DishesCategoryInfo dishesCategoryInfo) {
        List<DishesCategoryInfo.DishesCategoryListBean> dishesCategoryListBeanList =  dishesCategoryInfo.getDishesCategoryList();
        int count = dishesCategoryListBeanList.size();

        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0 ; i < count ; i++) {
            DishesCategoryInfo.DishesCategoryListBean dishesCategoryListBean = dishesCategoryListBeanList.get(i);

            final View titleBarItemView = inflater.inflate(R.layout.title_bar_item,null);
            TextView  nameTv = (TextView) titleBarItemView.findViewById(R.id.title_bar_item_name_tv);
            titleBarItemView.setTag(dishesCategoryListBean.getCategoryId());
            titleBarItemView.setTag(R.id.tag_first,i);

            nameTv.setText(UtilTools.decodeStr(dishesCategoryListBean.getCategoryName()) + "\n" + "hot");
            if (mTtitleBarMenuLl.getChildCount() == 0) {
                titleBarItemView.setBackgroundResource(R.drawable.title_bar_left_menu_normal_shape);
            } else {
                titleBarItemView.setBackgroundResource(R.color.title_bar_normal_color);
            }

            titleBarItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetTitleBarBg();

                    int titileBarIndex = (int)titleBarItemView.getTag(R.id.tag_first);
                    if (titileBarIndex == 0) {
                        v.setBackgroundResource(R.drawable.title_bar_left_menu_selected_shape);
                    } else {
                        v.setBackgroundResource(R.color.title_bar_selected_color);
                    }

                    mainMenuFragment.clearAllData();
                    String classType = v.getTag().toString();
                    String token = PreferenceUtil.getStringKey("token");
                    String storeId = PreferenceUtil.getStringKey(PreferenceUtil.STORE_ID);
                    mainMenuFragment.showContentMenu(token,storeId,classType);
                }
            });
            mTtitleBarMenuLl.addView(titleBarItemView);
        }

        View firstTitleItemView = mTtitleBarMenuLl.getChildAt(0);
        if (firstTitleItemView != null) {
            firstTitleItemView.performClick();
        }
    }

    private void resetTitleBarBg() {
        int count = mTtitleBarMenuLl.getChildCount();
        for (int i = 0 ; i < count ; i++) {
            View titleBarItem = mTtitleBarMenuLl.getChildAt(i);
            if (i == 0) {
                titleBarItem.setBackgroundResource(R.drawable.title_bar_left_menu_normal_shape);
            } else {
                titleBarItem.setBackgroundResource(R.color.title_bar_normal_color);
            }
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

    public void refreshCartAnimation(int fromLocation[]) {
        if (cartLocation == null) {
            cartLocation = new int[2];
            titleBarCarNumTv.getLocationOnScreen(cartLocation);
        }

        UtilTools.addOneDishes(this,fromLocation,cartLocation);
    }

    @Override
    public void showIngredientsMenuList(IngredientsMenuInfo ingredientsMenuInfo) {

    }

    public void refreshCartNum() {
    }

}
