package com.yc.wsjt.presenter;

import android.content.Context;

import com.yc.wsjt.base.BasePresenterImp;
import com.yc.wsjt.bean.HomeDateInfoRet;
import com.yc.wsjt.model.HomeInfoModelImp;
import com.yc.wsjt.view.HomeInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class HomeInfoPresenterImp extends BasePresenterImp<HomeInfoView, HomeDateInfoRet> implements HomeInfoPresenter {

    private Context context = null;
    private HomeInfoModelImp homeInfoModelImp = null;

    public HomeInfoPresenterImp(HomeInfoView view, Context context) {
        super(view);
        this.context = context;
        this.homeInfoModelImp = new HomeInfoModelImp(context);
    }

    @Override
    public void getHomeList() {
        homeInfoModelImp.getHomeList(this);
    }
}
