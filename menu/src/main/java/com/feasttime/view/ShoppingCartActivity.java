package com.feasttime.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasttime.adapter.MyOrderAdapter;
import com.feasttime.adapter.RecommendOrderAdapter;
import com.feasttime.menu.R;
import com.feasttime.model.CachedData;
import com.feasttime.model.bean.MyOrderListItemInfo;
import com.feasttime.model.bean.OrderInfo;
import com.feasttime.model.bean.RecommendOrderListItemInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.order.OrderContract;
import com.feasttime.presenter.order.OrderPresenter;
import com.feasttime.presenter.shoppingcart.ShoppingCartContract;
import com.feasttime.presenter.shoppingcart.ShoppingCartPresenter;
import com.feasttime.rxbus.RxBus;
import com.feasttime.rxbus.event.OrderEvent;
import com.feasttime.tools.LogUtil;
import com.feasttime.tools.PreferenceUtil;
import com.feasttime.tools.ScreenTools;
import com.feasttime.widget.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2017/8/16.
 */

public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener,ShoppingCartContract.IShoppingCartView,OrderContract.IOrderView{

    @Bind(R.id.normal_title_bar_back_iv)
    ImageView backIv;

    @Bind(R.id.normal_title_bar_title_tv)
    TextView titleTv;

    @Bind(R.id.shopping_cart_activity_total_price_tv)
    TextView totalPriceTv;

    @Bind(R.id.shopping_cart_activity_place_order_tv)
    TextView placeOrderTv;

    @Bind(R.id.shopping_cart_activity_recommend_rv)
    RecyclerView recommendRv;

    @Bind(R.id.shopping_cart_activity_order_rv)
    RecyclerView orderRv;


    @Bind(R.id.shopping_cart_activity_onion_cb)
    CheckBox onionCb;

    @Bind(R.id.shopping_cart_activity_ginger_cb)
    CheckBox gingerCb;

    @Bind(R.id.shopping_cart_activity_top_dash_line_iv)
    ImageView topDashlineIv;

    @Bind(R.id.shopping_cart_activity_bottom_dash_line_iv)
    ImageView btmDashlineIv;


    MyOrderAdapter myOrderAdapter;
    RecommendOrderAdapter recommendOrderAdapter;

    OrderPresenter mOrderPresenter = new OrderPresenter();
    ShoppingCartPresenter mShoppingCartPresenter = new ShoppingCartPresenter();
    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[]{mOrderPresenter,mShoppingCartPresenter};
    }

    @Override
    protected void onInitPresenters() {
        mOrderPresenter.init(this);
        mShoppingCartPresenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.shopping_cart_activity;
    }

    @Override
    protected void initViews() {
        titleTv.setText("购物车");
        topDashlineIv.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        btmDashlineIv.setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        List<RecommendOrderListItemInfo> recommendOrderListItemInfos = CachedData.orderInfo.getRecommendOrderList();
        List<MyOrderListItemInfo> myOrderListItemInfos = CachedData.orderInfo.getMyOrderList();
        totalPriceTv.setText(CachedData.orderInfo.getTotalPrice() + "元");

        recommendOrderAdapter = new RecommendOrderAdapter(recommendOrderListItemInfos,this);
//        recommendOrderAdapter.setListener(this);
        recommendRv.setLayoutManager(new LinearLayoutManager(this));
        recommendRv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, 10, Color.TRANSPARENT));
        recommendRv.setAdapter(recommendOrderAdapter);


        myOrderAdapter = new MyOrderAdapter(myOrderListItemInfos,this);
//        myOrderAdapter.setOrderModifyListener(this);
        orderRv.setLayoutManager(new LinearLayoutManager(this));
        orderRv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, 30, Color.TRANSPARENT));
        orderRv.setAdapter(myOrderAdapter);

        //totalPriceTv.setText(orderInfo.getTotalPrice());
        RxBus.getDefault().register(this, OrderEvent.class, new Consumer<OrderEvent>() {
            @Override
            public void accept(OrderEvent orderEvent) throws Exception {
                if (orderEvent.eventType == OrderEvent.ADD_ONE_DISHES) {
                    mShoppingCartPresenter.addShoppingCart(orderEvent.menuItemInfo);
                } else if (orderEvent.eventType == OrderEvent.REMOVE_ONE_DISHES) {
                    mShoppingCartPresenter.removeShoppingCart(orderEvent.menuItemInfo.getDishId());
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @OnCheckedChanged({R.id.shopping_cart_activity_onion_cb,R.id.shopping_cart_activity_ginger_cb})
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            showDialog(this,buttonView);
        }
    }

    @OnClick({R.id.normal_title_bar_back_iv,R.id.shopping_cart_activity_place_order_tv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == placeOrderTv) {
            String orderID = PreferenceUtil.getStringKey("orderID");
            mOrderPresenter.placeOrder(orderID);
        }
    }

    private void showDialog(Activity context, View anchorView) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final LayoutInflater li = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View dialogContentView = li.inflate(R.layout.no_eat_filter_dialog, null);
        final ImageView arrowIv = (ImageView) dialogContentView.findViewById(R.id.not_eat_filter_dialog_arrow_iv);

        dialog.setContentView(dialogContentView);
        final Window dialogWindow = dialog.getWindow();
        final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL);

        lp.width = 940; // 宽度
        lp.height = 882;

        final int anchorViewLocation[] = new int[2];
        anchorView.getLocationOnScreen(anchorViewLocation);
        final int anchorViewHeight = anchorView.getHeight();
        final int anchorViewWidth = anchorView.getWidth();

        dialogContentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int width = dialogContentView.getWidth();
                int height = dialogContentView.getHeight();

                lp.y = anchorViewLocation[1] - lp.height - 115; // 新位置Y坐标
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) arrowIv.getLayoutParams();

                int arrowLocation[] = new int[2];
                arrowIv.getLocationOnScreen(arrowLocation);

                params.leftMargin = anchorViewLocation[0] - arrowLocation[0] + anchorViewWidth / 4;
                arrowIv.setLayoutParams(params);
                dialogWindow.setAttributes(lp);
                dialogContentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        dialog.show();
    }

    @Override
    public void addShoppingCartComplete(OrderInfo orderInfo) {
        myOrderAdapter.refreshList(orderInfo.getMyOrderList());
        recommendOrderAdapter.refreshList(orderInfo.getRecommendOrderList());
        totalPriceTv.setText(orderInfo.getTotalPrice() + "元");
    }

    @Override
    public void removeShoppingCartComplete(OrderInfo orderInfo) {
        myOrderAdapter.refreshList(orderInfo.getMyOrderList());
        recommendOrderAdapter.refreshList(orderInfo.getRecommendOrderList());
        totalPriceTv.setText(orderInfo.getTotalPrice() + "元");
    }

    @Override
    public void getShoppingcartListComplete(OrderInfo orderInfo) {
        totalPriceTv.setText(orderInfo.getTotalPrice() + "元");
    }

    @Override
    public void showRecommendList(List<RecommendOrderListItemInfo> recommendOrderList) {
    }

    @Override
    public void showOrderList(List<MyOrderListItemInfo> myOrderList) {
    }

    @Override
    public void createOrderComplete() {
    }

    @Override
    public void payOrderComplete() {
    }

    @Override
    public void placeOrderComplete() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }
}
