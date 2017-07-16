/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.presenter.user;

import com.feasttime.model.bean.DishesCategoryInfo;
import com.feasttime.model.bean.MenuItemInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.IBaseView;
import com.feasttime.presenter.menu.MenuContract;

public interface UserContract {
    interface IUserView extends IBaseView {
        void loginSuccess();
        void registerSuccess();
    }

    interface IUserPresenter extends IBasePresenter<IUserView> {
        void register(String mobileNO);
        void login(String mobileNO);
    }
}
