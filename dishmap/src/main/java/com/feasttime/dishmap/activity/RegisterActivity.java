package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.LoginInfo;
import com.feasttime.dishmap.model.bean.RegisterInfo;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.ToastUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2017/10/15.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.activity_register_phone_et)
    EditText phoneEt;

    @Bind(R.id.activity_register_password_et)
    EditText passwordEt;

    @Bind(R.id.activity_register_get_verify_num_btn)
    Button getVerifyNumBtn;

    @Bind(R.id.activity_register_name_et)
    EditText nameEt;

    @Bind(R.id.activity_register_register_btn)
    Button registerBtn;

    @Bind(R.id.title_bar_share_iv)
    ImageView titleShareIv;

    @Bind(R.id.title_back_iv)
    ImageView backIv;

    @Bind(R.id.title_center_text_tv)
    TextView centerTitleTv;

    @Bind(R.id.activity_register_step1_ll)
    LinearLayout step1Ll;

    @Bind(R.id.activity_register_step2_ll)
    LinearLayout step2Ll;

    @Bind(R.id.activity_register_vierfy_number_sended_tv)
    TextView verifyNumSendedTv;

    @Bind(R.id.activity_register_type_cb)
    CheckBox typeCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleShareIv.setVisibility(View.GONE);
        centerTitleTv.setText("注册");

        typeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setText("个人");
                } else {
                    buttonView.setText("饭店");
                }
            }
        });
    }


    @OnClick({R.id.activity_register_get_verify_num_btn,R.id.title_back_iv,R.id.activity_register_register_btn})
    @Override
    public void onClick(View v) {
        if (v == getVerifyNumBtn) {
            String phone = phoneEt.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast(this,"手机号不能为空",Toast.LENGTH_SHORT);
                return;
            }
            PreferenceUtil.setStringKey(PreferenceUtil.MOBILE_NO,phone);
            step1Ll.setVisibility(View.GONE);
            step2Ll.setVisibility(View.VISIBLE);
            verifyNumSendedTv.setText("验证码已经发送到：" + phone);
        } else if (v == registerBtn) {
            String phone = phoneEt.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();
            String name = nameEt.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                ToastUtil.showToast(this,"姓名不能为空", Toast.LENGTH_SHORT);
                return;
            }

            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast(this,"手机号不能为空", Toast.LENGTH_SHORT);
                return;
            }

            if (TextUtils.isEmpty(password)) {
                ToastUtil.showToast(this,"密码不能为空", Toast.LENGTH_SHORT);
                return;
            }

            HashMap<String,Object> infoMap = new HashMap<String,Object>();
            infoMap.put("mobileNO",phone);
            infoMap.put("pwd",password);
            infoMap.put("name",name);
            if (typeCb.isChecked()) {
                infoMap.put("userType","customer");
            } else {
                infoMap.put("userType","store");
            }

            RetrofitService.register(infoMap).subscribe(new Consumer<RegisterInfo>(){
                @Override
                public void accept(RegisterInfo registerInfo) throws Exception {
                    if (registerInfo.getResultCode() == 0) {
                        ToastUtil.showToast(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT);
                        finish();
                    } else {
                        ToastUtil.showToast(RegisterActivity.this,registerInfo.getResultMsg(),Toast.LENGTH_SHORT);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                }
            }, new Action() {
                @Override
                public void run() throws Exception {
                }
            });
        } else if (v == backIv) {
            if (step2Ll.getVisibility() == View.VISIBLE) {
                step1Ll.setVisibility(View.VISIBLE);
                step2Ll.setVisibility(View.GONE);
            } else {
                finish();
            }

        }
    }
}
