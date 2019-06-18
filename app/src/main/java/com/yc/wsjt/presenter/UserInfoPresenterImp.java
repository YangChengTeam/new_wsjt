package com.yc.wsjt.presenter;

import android.content.Context;

import com.yc.wsjt.base.BasePresenterImp;
import com.yc.wsjt.bean.UserInfo;
import com.yc.wsjt.bean.UserInfoRet;
import com.yc.wsjt.model.UserInfoModelImp;
import com.yc.wsjt.view.UserInfoView;

/**
 * Created by admin on 2017/4/7.
 */

public class UserInfoPresenterImp extends BasePresenterImp<UserInfoView, UserInfoRet> implements UserInfoPresenter {

    private Context context = null;
    private UserInfoModelImp userInfoModelImp = null;

    public UserInfoPresenterImp(UserInfoView view, Context context) {
        super(view);
        this.context = context;
        this.userInfoModelImp = new UserInfoModelImp(context);
    }

    @Override
    public void userInfo(UserInfo userInfo) {
        userInfoModelImp.userInfo(userInfo, this);
    }

    @Override
    public void updateName(UserInfo userInfo) {
        userInfoModelImp.updateName(userInfo, this);
    }
}
