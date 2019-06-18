package com.yc.wsjt.presenter;

import android.content.Context;

import com.yc.wsjt.base.BasePresenterImp;
import com.yc.wsjt.bean.ModuleInfoRet;
import com.yc.wsjt.model.ModuleInfoModelImp;
import com.yc.wsjt.view.ModuleInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class ModuleInfoPresenterImp extends BasePresenterImp<ModuleInfoView, ModuleInfoRet> implements ModuleInfoPresenter {

    private Context context = null;
    private ModuleInfoModelImp moduleInfoModelImp = null;

    public ModuleInfoPresenterImp(ModuleInfoView view, Context context) {
        super(view);
        this.context = context;
        this.moduleInfoModelImp = new ModuleInfoModelImp(context);
    }

    @Override
    public void getModuleList() {
        moduleInfoModelImp.getModuleList(this);
    }

}
