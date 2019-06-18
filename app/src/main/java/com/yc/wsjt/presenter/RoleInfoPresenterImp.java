package com.yc.wsjt.presenter;

import android.content.Context;

import com.yc.wsjt.base.BasePresenterImp;
import com.yc.wsjt.bean.RoleInfoRet;
import com.yc.wsjt.model.RoleInfoModelImp;
import com.yc.wsjt.view.RoleInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class RoleInfoPresenterImp extends BasePresenterImp<RoleInfoView, RoleInfoRet> implements RoleInfoPresenter {

    private Context context = null;
    private RoleInfoModelImp roleInfoModelImp = null;

    public RoleInfoPresenterImp(RoleInfoView view, Context context) {
        super(view);
        this.context = context;
        this.roleInfoModelImp = new RoleInfoModelImp(context);
    }

    @Override
    public void getRoleList() {
        roleInfoModelImp.getRoleList(this);
    }
}
