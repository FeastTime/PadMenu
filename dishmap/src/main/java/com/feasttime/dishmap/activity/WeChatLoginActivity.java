package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.utils.ToastUtil;
import com.feasttime.dishmap.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 2018/3/5.
 */

public class WeChatLoginActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.activity_wechat_login_to_login_tv)
    TextView loginTv;

    @Bind(R.id.activity_wechat_login_aggree_iv)
    ImageView agreeIv;

    @Bind(R.id.activity_wechat_login_user_aggreement_tv)
    TextView agreementTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.activity_wechat_login_to_login_tv,R.id.activity_wechat_login_aggree_iv,R.id.activity_wechat_login_user_aggreement_tv})
    @Override
    public void onClick(View v) {
        if (v == loginTv) {
            if (agreeIv.getTag() != null) {
                ToastUtil.showToast(this,"请先同意协议", Toast.LENGTH_SHORT);
            } else {
                UtilTools.loginWithWeChat(this);
                finish();
            }

        } else if (v == agreeIv) {
            if (agreeIv.getTag() == null) {
                agreeIv.setImageResource(R.mipmap.orange_right_no_select);
                agreeIv.setTag("no_select");
            } else {
                agreeIv.setImageResource(R.mipmap.orange_right);
                agreeIv.setTag(null);
            }
        } else if (v == agreementTv) {
            startActivity(new Intent(this,UserAgreementActivity.class));
        }
    }
}
