package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.RedPackageDetailAdapter;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.RedPackageDetail;
import com.feasttime.dishmap.model.bean.RedPackageDetailItemInfo;
import com.feasttime.dishmap.utils.CircleImageTransformation;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 *
 * Created by li on 2018/1/19.
 */

public class OpenedRedPackageActivity extends BaseActivity {

    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;

    @Bind(R.id.user_icon)
    ImageView userIcon;

    @Bind(R.id.whos_package)
    TextView whosPackage;

    @Bind(R.id.lucky_title)
    TextView luckyTitle;

    @Bind(R.id.activity_message_lv)
    ListView contentLv;

    private String redPackageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opened_red_package);
        ButterKnife.bind(this);
        redPackageId = this.getIntent().getStringExtra("redPackageId");
        initViews();


    }

    private void initViews() {

        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("红包详情");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);

        HashMap<String,Object> infoMap = new HashMap<>();

        final String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        infoMap.put("token",token);
        infoMap.put("userId",userId);
        infoMap.put("redPackageId",redPackageId);


        RetrofitService.queryRedPackageDetail(infoMap).subscribe(new Consumer<RedPackageDetailItemInfo>(){
            @Override
            public void accept(RedPackageDetailItemInfo redPackageDetailItemInfo) throws Exception {

                if (redPackageDetailItemInfo.getResultCode() == 0) {

                    if (null == redPackageDetailItemInfo.getRedPackageDetailMap()
                            || redPackageDetailItemInfo.getRedPackageDetailMap().size() == 0){
                        return;
                    }

                    if (redPackageDetailItemInfo.getRedPackageDetailMap().containsKey(userId)){

                        RedPackageDetail myRedPackageDetail = redPackageDetailItemInfo.getRedPackageDetailMap().get(userId);

                        whosPackage.setText(myRedPackageDetail.getNickName() + "的红包");
                        luckyTitle.setText(myRedPackageDetail.getRedPackageTitle());

                        if (!StringUtils.isEmpty(myRedPackageDetail.getUserIcon())){
                            Picasso.with(OpenedRedPackageActivity.this).load(myRedPackageDetail.getUserIcon()).transform(new CircleImageTransformation()).into(userIcon);
                        }

                    }

                    ArrayList<RedPackageDetail> redPackageDetailList = new ArrayList<>();

                    for (RedPackageDetail redPackageDetail : redPackageDetailItemInfo.getRedPackageDetailMap().values()){
                        redPackageDetailList.add(redPackageDetail);
                    }


                    RedPackageDetailAdapter messageAdapter = new RedPackageDetailAdapter(OpenedRedPackageActivity.this, redPackageDetailList);
                    contentLv.setAdapter(messageAdapter);



                } else {

                    Toast.makeText(OpenedRedPackageActivity.this, OpenedRedPackageActivity.this.getString(R.string.internet_disconnected_toast), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(OpenedRedPackageActivity.this, OpenedRedPackageActivity.this.getString(R.string.internet_disconnected_toast), Toast.LENGTH_SHORT).show();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
            }
        });








    }
}
