package com.feasttime.presenter.shoppingcart;

import com.feasttime.model.bean.MenuItemInfo;
import com.feasttime.model.bean.MyOrderListItemInfo;
import com.feasttime.model.bean.OrderInfo;
import com.feasttime.model.bean.RecommendOrderListItemInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.IBaseView;

import java.util.List;

/**
 * Created by chen on 2017/4/19.
 */

public interface ShoppingCartContract {
    interface IShoppingCartView extends IBaseView {
        void addShoppingCartComplete(OrderInfo orderInfo);
        void removeShoppingCartComplete(OrderInfo orderInfo);
        void getShoppingcartListComplete(OrderInfo orderInfo);
        void showRecommendList(List<RecommendOrderListItemInfo> recommendOrderList);
        void showOrderList(List<MyOrderListItemInfo> myOrderList);
    }

    interface IShoppingCartPresenter extends IBasePresenter<IShoppingCartView> {
        void addShoppingCart(MenuItemInfo menuItemInfo);
        void removeShoppingCart(String ID, String orderID);
        void getShoppingCartList(String orderID);
    }
}
