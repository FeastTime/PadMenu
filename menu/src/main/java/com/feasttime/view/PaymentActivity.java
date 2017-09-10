package com.feasttime.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasttime.adapter.OutDishesAdapter;
import com.feasttime.menu.R;
import com.feasttime.model.bean.MyOrderListItemInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.widget.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by chen on 2017/8/26.
 */

public class PaymentActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.normal_title_bar_main_bg_rel)
    RelativeLayout titleBgRel;

    @Bind(R.id.normal_title_bar_back_iv)
    ImageView backIv;

    @Bind(R.id.normal_title_bar_title_tv)
    TextView titleTv;

    @Bind(R.id.payment_activity_out_dishes_rv)
    RecyclerView outDishesRv;

    @Bind(R.id.payment_activity_dash_line)
    TextView dashlineTv;

    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[0];
    }

    @Override
    protected void onInitPresenters() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.payment_activity;
    }

    @Override
    protected void initViews() {
        titleTv.setText("付款页");
        dashlineTv.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        titleBgRel.setBackgroundColor(Color.TRANSPARENT);

        List<MyOrderListItemInfo> orderList = new ArrayList<MyOrderListItemInfo>();
        for (int i = 0 ; i < 4; i++) {
            MyOrderListItemInfo myOrderListItemInfo = new MyOrderListItemInfo();
            myOrderListItemInfo.setDishName("干锅土豆片");
            orderList.add(myOrderListItemInfo);
        }

        OutDishesAdapter outDishesAdapter = new OutDishesAdapter(orderList,this);
        outDishesRv.setLayoutManager(new GridLayoutManager(this,2));
        outDishesRv.addItemDecoration(new RecyclerViewDivider(this, GridLayoutManager.VERTICAL, 20, Color.TRANSPARENT));
        outDishesRv.setAdapter(outDishesAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @OnClick({R.id.normal_title_bar_back_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }
    }
}
