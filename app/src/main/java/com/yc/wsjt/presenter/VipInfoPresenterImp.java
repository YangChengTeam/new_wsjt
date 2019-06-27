package com.yc.wsjt.presenter;

import android.content.Context;

import com.yc.wsjt.base.BasePresenterImp;
import com.yc.wsjt.bean.VipInfoRet;
import com.yc.wsjt.model.VipInfoModelImp;
import com.yc.wsjt.view.VipInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class VipInfoPresenterImp extends BasePresenterImp<VipInfoView, VipInfoRet> implements VipInfoPresenter {

    private Context context = null;
    private VipInfoModelImp vipInfoModelImp = null;

    public VipInfoPresenterImp(VipInfoView view, Context context) {
        super(view);
        this.context = context;
        this.vipInfoModelImp = new VipInfoModelImp(context);
    }

    @Override
    public void getVipList() {
        vipInfoModelImp.getVipList(this);
    }
}
