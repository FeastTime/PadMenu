package com.feasttime.presenter.menu;


import com.feasttime.model.bean.DishesCategoryInfo;
import com.feasttime.model.bean.MenuInfo;
import com.feasttime.model.bean.MenuItemInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.IBaseView;


public interface MenuContract {
    interface IMenuView extends IBaseView {
        void showMenu(MenuInfo result);
        void showDishesCategory(DishesCategoryInfo.DishesCategoryListBean dishesCategoryListBean);
    }

    interface IMenuPresenter extends IBasePresenter<IMenuView> {
        void getMenu(String token, String orderID, String classType, String page);
        void getDishesCategory();
    }
}
