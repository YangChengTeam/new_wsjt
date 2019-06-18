package com.yc.wsjt.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.HomeDateInfoRet;
import com.yc.wsjt.bean.QuickInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.HomeInfoPresenterImp;
import com.yc.wsjt.ui.activity.BaseActivity;
import com.yc.wsjt.ui.activity.QuickBarEditActivity;
import com.yc.wsjt.ui.activity.WeiXinModuleActivity;
import com.yc.wsjt.ui.activity.WeixindanliaoActivity;
import com.yc.wsjt.ui.adapter.QuickAdapter;
import com.yc.wsjt.ui.custom.GlideImageLoader;
import com.yc.wsjt.ui.custom.LoginDialog;
import com.yc.wsjt.view.HomeInfoView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.PermissionRequest;

/**
 * Created by admin on 2017/4/20.
 */

public class HomeFragment extends BaseFragment implements HomeInfoView, View.OnClickListener {

    @BindView(R.id.quick_list)
    RecyclerView quickListView;

    QuickAdapter quickAdapter;

    View headView;

    Banner mBanner;

    LinearLayout mWeiXinLayout;

    LinearLayout mAlipayLayout;

    LinearLayout mQQLayout;

    TextView mEditTv;

    HomeInfoPresenterImp homeInfoPresenterImp;

    @BindView(R.id.fake_status_bar)
    View mFakeStatusBar;

    LoginDialog loginDialog;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initVars() {

    }

    @Override
    public void initViews() {
        setTopViewBgColor();
        //setTvTitleBackgroundColor(Color.TRANSPARENT);

        quickAdapter = new QuickAdapter(getActivity(), null, ScreenUtils.getScreenWidth() / 3);
        quickListView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        quickListView.setAdapter(quickAdapter);

        //头部view
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.home_head_view, null);
        mBanner = headView.findViewById(R.id.banner);
        mWeiXinLayout = headView.findViewById(R.id.layout_weixin);
        mAlipayLayout = headView.findViewById(R.id.layout_alipay);
        mQQLayout = headView.findViewById(R.id.layout_qq);

        mWeiXinLayout.setOnClickListener(this);
        mAlipayLayout.setOnClickListener(this);
        mQQLayout.setOnClickListener(this);

        mEditTv = headView.findViewById(R.id.tv_edit);
        quickAdapter.setHeaderView(headView);

        quickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == quickAdapter.getData().size() - 1) {
                    Intent intent = new Intent(getActivity(), QuickBarEditActivity.class);
                    startActivity(intent);
                } else {

                    if (!App.getApp().isLogin) {
                        if (loginDialog != null && !loginDialog.isShowing()) {
                            loginDialog.show();
                        }
                        return;
                    }

                    Intent intent = new Intent(getActivity(), WeixindanliaoActivity.class);
                    startActivity(intent);
                }
            }
        });

        mEditTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuickBarEditActivity.class);
                startActivity(intent);
            }
        });

        loginDialog = new LoginDialog(getActivity(), R.style.custom_dialog);

    }

    public void setTopViewBgColor() {
        StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.white), 0);
        StatusBarUtil.setLightMode(getActivity());
    }

    @Override
    public void loadData() {
        homeInfoPresenterImp = new HomeInfoPresenterImp(this, getActivity());
        homeInfoPresenterImp.getHomeList();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!SPUtils.getInstance().getBoolean(Constants.HOME_IS_FIRST_LOAD, true)) {
            try {
                ((BaseActivity) getActivity()).mAppDatabase.quickInfoDao()
                        .loadQuickInfo()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<QuickInfo>>() {
                            @Override
                            public void accept(List<QuickInfo> list) {
                                if (list != null) {
                                    quickAdapter.setNewData(list);
                                    addPlus();
                                }
                            }
                        });
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPlus() {
        boolean isFlag = false;
        for (int i = 0; i < quickAdapter.getData().size(); i++) {
            if (quickAdapter.getData().get(i).getLocalImg() == R.mipmap.home_quick_add) {
                isFlag = true;
            }
        }
        if (!isFlag) {
            //添加一条
            QuickInfo quickInfo = new QuickInfo();
            quickInfo.setLocalImg(R.mipmap.home_quick_add);
            quickAdapter.addData(quickInfo);
        }
    }

    @Override
    protected void initFragmentConfig() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(HomeDateInfoRet tData) {
        Logger.i("home result --->" + JSON.toJSONString(tData));
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null) {
                if (tData.getData().getBanner() != null && tData.getData().getBanner().size() > 0) {
                    List<String> bannerList = new ArrayList<>();
                    for (int i = 0; i < tData.getData().getBanner().size(); i++) {
                        bannerList.add(Constants.BASE_IMAGE_URL + tData.getData().getBanner().get(i).getImg());
                    }
                    // 设置数据
                    //设置图片加载器
                    mBanner.setImageLoader(new GlideImageLoader());
                    //设置图片集合
                    mBanner.setImages(bannerList);
                    //banner设置方法全部调用完毕时最后调用
                    mBanner.start();
                }

                if (SPUtils.getInstance().getBoolean(Constants.HOME_IS_FIRST_LOAD, true)) {
                    if (tData.getData().getTool() != null && tData.getData().getTool().size() > 0) {
                        SPUtils.getInstance().put(Constants.HOME_IS_FIRST_LOAD, false);
                        List<QuickInfo> tempList = tData.getData().getTool();
                        quickAdapter.setNewData(tempList);
                        //Logger.i("insert quick info--->" + JSON.toJSONString(tempList));
                        for (int i = 0; i < tempList.size(); i++) {
                            tempList.get(i).setAddDate(TimeUtils.date2Millis(new Date()));
                        }
                        ((BaseActivity) getActivity()).mAppDatabase.quickInfoDao().insertList(tempList);
                    }
                    addPlus();
                }
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_weixin:
                Intent intent = new Intent(getActivity(), WeiXinModuleActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_alipay:

                break;
            case R.id.layout_qq:

                break;
            default:
                break;
        }
    }
}
