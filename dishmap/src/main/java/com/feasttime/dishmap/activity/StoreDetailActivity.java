package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.third.onekeyshare.ShareMain;

/**
 * Created by chen on 2017/9/20.
 */

public class StoreDetailActivity extends BaseActivity implements View.OnClickListener{
    TextView titleTv;
    RelativeLayout menuBgRel;
    ImageView showMenuIv;
    LinearLayout otherMenuLl;
    ImageView orderDishesIv;
    ImageView liveStreaming;
    ImageView backIv;
    ImageView contentIv;
    ImageView titleShareIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        titleTv = (TextView)this.findViewById(R.id.title_text_tv);
        menuBgRel = (RelativeLayout)this.findViewById(R.id.activity_store_detail_menu_bg_rel);
        showMenuIv = (ImageView)this.findViewById(R.id.activity_store_detail_menu_show_menu_iv);
        otherMenuLl = (LinearLayout)this.findViewById(R.id.activity_store_detail_menu_other_menu_ll);
        orderDishesIv = (ImageView)this.findViewById(R.id.activity_store_detail_menu_order_iv);
        liveStreaming = (ImageView)this.findViewById(R.id.activity_store_detail_menu_live_iv);
        backIv = (ImageView)this.findViewById(R.id.title_back_iv);
        contentIv = (ImageView)this.findViewById(R.id.activity_store_detail_content_iv);
        titleShareIv = (ImageView)this.findViewById(R.id.title_bar_share_iv);

        titleTv.setText("PavoMea");
        titleTv.setOnClickListener(this);
        showMenuIv.setOnClickListener(this);
        orderDishesIv.setOnClickListener(this);
        liveStreaming.setOnClickListener(this);
        backIv.setOnClickListener(this);
        titleShareIv.setOnClickListener(this);
        menuBgRel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideMenu();
                return false;
            }
        });

        calcContentSize();
    }

    private void calcContentSize() {
//        int imgSize[] = UtilTools.getImageWidthHeight(this,R.mipmap.store_detail_content);
//        WindowManager wm = this.getWindowManager();
//
//        int width = wm.getDefaultDisplay().getWidth();
//        int calcHeight = (width * imgSize[1]) / imgSize[0];
//
//        ViewGroup.LayoutParams params = contentIv.getLayoutParams();
//        params.width = width;
//        params.height = calcHeight;
//        contentIv.setLayoutParams(params);
    }

    @Override
    public void onClick(View view) {
        if (view == titleTv || view == backIv) {
//            finish();
            ShareMain.share(this);
        } else if (view == showMenuIv) {
            if (showMenuIv.getTag() == null) {
                showMenu();
            } else {
                hideMenu();
            }
        } else if (view == orderDishesIv) {
            startActivity(new Intent(this,MenuActivity.class));
//            finish();
        } else if (view == liveStreaming) {


                Intent intent = new Intent(this, VideoActivity.class);
//                intent.putExtra("id", clickMyMarkerInfo.getStoreId());
                startActivity(intent);


//            finish();
        } else if (view == titleShareIv) {
            ShareMain.share(view.getContext());
        }
    }

    private void showMenu() {
        menuBgRel.setBackgroundColor(Color.parseColor("#AA000000"));
        showMenuIv.setImageResource(R.mipmap.x_icon);
        showMenuIv.setTag("show");
        otherMenuLl.setVisibility(View.VISIBLE);
    }

    private void hideMenu() {
        showMenuIv.setTag(null);
        menuBgRel.setBackgroundColor(Color.parseColor("#00000000"));
        showMenuIv.setImageResource(R.mipmap.plus_icon);
        otherMenuLl.setVisibility(View.GONE);
    }
}
