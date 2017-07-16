package com.feasttime.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.IBaseView;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;

/**
 * Created by chen on 2017/5/11.
 */

public abstract class BaseFragment extends Fragment implements IBaseView {
    public View rootView;

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

    private void addPresenters() {
        IBasePresenter[] presenters = getPresenters();
        if (presenters != null) {
            for (int i = 0; i < presenters.length; i++) {
                mAllPresenters.add(presenters[i]);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutResId(),container,false);
        ButterKnife.bind(this,rootView);
        initViews();
        addPresenters();
        onInitPresenters();
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        //依次调用IPresenter的onResume方法
        for (IBasePresenter presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.onResume();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //依次调用IPresenter的onStop方法
        for (IBasePresenter presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.onStop();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //依次调用IPresenter的onPause方法
        for (IBasePresenter presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.onPause();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //依次调用IPresenter的onStart方法
        for (IBasePresenter presenter : mAllPresenters) {
            if (presenter != null) {
                presenter.onStart();
            }
        }
    }

    @Override
    public void onDestroy() {
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNetError() {

    }

    @Override
    public void finishRefresh() {

    }

    @Override
    public void showTransparentCoverView() {

    }

}
