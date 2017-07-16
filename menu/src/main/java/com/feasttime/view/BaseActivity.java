package com.feasttime.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.IBaseView;
import com.feasttime.tools.ToastUtil;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;

/**
 * Created by chen on 2017/4/16.
 * 600/370           490/250             270/215
 */

public abstract class BaseActivity extends FragmentActivity implements IBaseView {

    ProgressDialog m_pDialog;
    boolean isShowProgressDialog = false;

    private Set<IBasePresenter> mAllPresenters = new HashSet<>(1);
    /**
     * 需要子类来实现，获取子类的IPresenter，一个activity有可能有多个IPresenter
     */
    protected abstract IBasePresenter[] getPresenters();
    /**
     * 初始化presenters
     */
    protected abstract void onInitPresenters();

    /**
     * 获取layout的id，具体由子类实现
     *
     * @return
     */
    protected abstract int getLayoutResId();


    protected abstract void initViews();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//remove notification bar  即全屏

        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        initViews();
        addPresenters();
        onInitPresenters();
    }

    private void addPresenters() {
        IBasePresenter[] presenters = getPresenters();
        if (presenters != null) {
            for (int i = 0; i < presenters.length; i++) {
                mAllPresenters.add(presenters[i]);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //依次调用IPresenter的onResume方法
        for (IBasePresenter presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.onResume();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //依次调用IPresenter的onStop方法
        for (IBasePresenter presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.onStop();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //依次调用IPresenter的onPause方法
        for (IBasePresenter presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.onPause();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //依次调用IPresenter的onStart方法
        for (IBasePresenter presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.onStart();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //依次调用IPresenter的onDestroy方法
        for (IBasePresenter presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.onDestroy();
            }
        }
    }

    @Override
    public void showLoading() {


        if (isShowProgressDialog)
            return;

        isShowProgressDialog = true;

//创建ProgressDialog对象
        m_pDialog = new ProgressDialog(this);

// 设置进度条风格，风格为圆形，旋转的
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // 设置ProgressDialog 提示信息
        m_pDialog.setMessage("请稍等。。。");

        // 设置ProgressDialog 的进度条是否不明确
        m_pDialog.setIndeterminate(false);

        // 设置ProgressDialog 是否可以按退回按键取消
        m_pDialog.setCancelable(false);

        m_pDialog.show();
    }

    @Override
    public void hideLoading() {

        if (isShowProgressDialog){

            m_pDialog.cancel();
            isShowProgressDialog = false;
        }
    }

    @Override
    public void showNetError() {
        ToastUtil.showToast(this,"请求失败", Toast.LENGTH_SHORT);
    }

    @Override
    public void finishRefresh() {

    }

    @Override
    public void showTransparentCoverView() {

    }
}
