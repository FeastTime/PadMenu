package com.feasttime.dishmap.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.feasttime.dishmap.R;


public class BaseActivity extends Activity {

    ProgressDialog m_pDialog;
    boolean isShowProgressDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setStatusBarTransparent(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView backIv = (ImageView) this.findViewById(R.id.title_back_iv);
        if (backIv != null) {
            backIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void setStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
                //window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
                //attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    // 设置标题栏全透明
//    public void setTitleBarFullTransparents(){
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
//    }


    public void showLoading(String message) {


        if (isShowProgressDialog)
            return;

        isShowProgressDialog = true;

        //创建ProgressDialog对象
        m_pDialog = new ProgressDialog(this,R.style.LoadingDialog);

        // 设置ProgressDialog 提示信息
        m_pDialog.setMessage(null != message ? message : "请稍等... ...");

        // 设置ProgressDialog 的进度条是否不明确
        m_pDialog.setIndeterminate(false);

        // 设置ProgressDialog 是否可以按退回按键取消
        m_pDialog.setCancelable(false);

        m_pDialog.show();

        View contentView = this.getLayoutInflater().inflate(R.layout.progress_layout,null);

        // 设置ProgressDialog 的布局
        m_pDialog.setContentView(contentView);

        Animation circle_anim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);

        //设置匀速旋转，在xml文件中设置会出现卡顿
        LinearInterpolator interpolator = new LinearInterpolator();

        circle_anim.setInterpolator(interpolator);

        ImageView loadingIcon = (ImageView) contentView.findViewById(R.id.progress_layout_loading_icon_iv);

        //开始动画
        if (circle_anim != null) {
            loadingIcon.startAnimation(circle_anim);
        }
    }


    public void hideLoading() {

        if (isShowProgressDialog){

            m_pDialog.cancel();
            isShowProgressDialog = false;
        }
    }



}
