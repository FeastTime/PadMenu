package com.feasttime.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
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

import com.feasttime.menu.R;
import com.feasttime.presenter.IBasePresenter;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by chen on 2017/8/16.
 */

public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    @Bind(R.id.normal_title_bar_back_iv)
    ImageView backIv;

    @Bind(R.id.normal_title_bar_title_tv)
    TextView titleTv;

    @Bind(R.id.shopping_cart_activity_place_order_tv)
    TextView placeOrderTv;

    @Bind(R.id.shopping_cart_activity_onion_cb)
    CheckBox onionCb;

    @Bind(R.id.shopping_cart_activity_ginger_cb)
    CheckBox gingerCb;

    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[0];
    }

    @Override
    protected void onInitPresenters() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.shopping_cart_activity;
    }

    @Override
    protected void initViews() {
        titleTv.setText("购物车");
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
            showDialog(this,gingerCb);
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

                lp.y = anchorViewLocation[1] - anchorViewHeight * 3 - height; // 新位置Y坐标
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
}
