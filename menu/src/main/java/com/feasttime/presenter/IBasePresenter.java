package com.feasttime.presenter;

/**
 *
 * Created by glh on 2016-06-23.
 */
public interface IBasePresenter<V extends IBaseView> {
    void onStop();

    void onResume();

    void onDestroy();

    void onPause();

    void onStart();

    void init(V view);
}
