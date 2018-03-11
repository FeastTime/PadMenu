package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.BaseResponseBean;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.ToastUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2018/1/12.
 */

public class FeedBackActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;

    @Bind(R.id.activity_feedback_content_et)
    EditText conentEt;

    @Bind(R.id.activity_feedback_contact_way_et)
    EditText contactWayEt;

    @Bind(R.id.activity_feedback_submit_btn)
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("意见反馈");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);
    }

    @OnClick({R.id.activity_feedback_submit_btn})
    @Override
    public void onClick(View v) {
        if (v == submitBtn) {
            String contentStr = conentEt.getText().toString();
            String contactWayStr = contactWayEt.getText().toString();

            if (TextUtils.isEmpty(contentStr.trim())) {
                ToastUtil.showToast(this,"请输入内容",Toast.LENGTH_SHORT);
                return;
            }

//            if (TextUtils.isEmpty(contactWayStr.trim())) {
//                ToastUtil.showToast(this,"请输入联系方式",Toast.LENGTH_SHORT);
//                return;
//            }

            requestFeedBack(contentStr,contactWayStr);
        }
    }

    private void requestFeedBack(String content, String contactWay) {

        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        String userID = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);

        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        infoMap.put("token",token);
        infoMap.put("userId",userID);

        showLoading(null);

        RetrofitService.feedback(infoMap).subscribe(new Consumer<BaseResponseBean>(){
            @Override
            public void accept(BaseResponseBean baseResponseBean) throws Exception {
                if (baseResponseBean.getResultCode() == 0) {
                    ToastUtil.showToast(FeedBackActivity.this,"成功", Toast.LENGTH_SHORT);
                    finish();
                } else {
                    ToastUtil.showToast(FeedBackActivity.this,"失败", Toast.LENGTH_SHORT);
                }
                hideLoading();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                hideLoading();
                ToastUtil.showToast(FeedBackActivity.this,"失败",Toast.LENGTH_SHORT);
            }
        }, new Action() {
            @Override
            public void run() throws Exception {

            }
        });
    }
}
