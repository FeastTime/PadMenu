package com.feasttime.presenter.order;

import com.feasttime.model.bean.MenuItemInfo;
import com.feasttime.model.bean.MyOrderListItemInfo;
import com.feasttime.model.bean.OrderInfo;
import com.feasttime.model.bean.RecommendOrderListItemInfo;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.presenter.IBaseView;
import com.feasttime.presenter.menu.MenuContract;

import java.util.List;

/**
 * Created by chen on 2017/5/11.
 */

public interface OrderContract {
    interface IOrderView extends IBaseView {
        void createOrderComplete();
        void payOrderComplete();
        void placeOrderComplete();
    }

    interface IOrderPresenter extends IBasePresenter<IOrderView> {
        void createOrder(String orderID);
        void payOrder(String orderID);
        void placeOrder(String orderID);

    }
}
