package com.yc.wsjt.presenter;

import android.content.Context;

import com.yc.wsjt.base.BasePresenterImp;
import com.yc.wsjt.bean.ResultInfo;
import com.yc.wsjt.model.UpdateInfoModelImp;
import com.yc.wsjt.view.UserInfoView;

import java.io.File;

/**
 * Created by admin on 2017/3/13.
 */

public class UpdateInfoPresenterImp extends BasePresenterImp<UserInfoView, ResultInfo> implements UpdateInfoPresenter {

    private Context context = null;
    private UpdateInfoModelImp updateInfoModelImp = null;

    public UpdateInfoPresenterImp(UserInfoView view, Context context) {
        super(view);
        this.context = context;
        this.updateInfoModelImp = new UpdateInfoModelImp(context);
    }

    @Override
    public void updateHead(String uid, File file) {
        updateInfoModelImp.updateHead(uid, file, this);
    }
}
