package com.yc.wsjt.presenter;

import android.content.Context;

import com.yc.wsjt.base.BasePresenterImp;
import com.yc.wsjt.bean.HeadInfoRet;
import com.yc.wsjt.model.HeadInfoModelImp;
import com.yc.wsjt.view.HeadInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class HeadInfoPresenterImp extends BasePresenterImp<HeadInfoView, HeadInfoRet> implements HeadInfoPresenter {

    private Context context = null;
    private HeadInfoModelImp headInfoModelImp = null;

    public HeadInfoPresenterImp(HeadInfoView view, Context context) {
        super(view);
        this.context = context;
        this.headInfoModelImp = new HeadInfoModelImp(context);
    }

    @Override
    public void getHeadList(int index) {
        headInfoModelImp.getHeadList(index, this);
    }
}
