package com.yc.wsjt.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMShareAPI;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.ModuleInfoRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.ModuleInfoPresenterImp;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.MyFragmentAdapter;
import com.yc.wsjt.ui.fragment.HomeFragment;
import com.yc.wsjt.ui.fragment.MyFragment;
import com.yc.wsjt.view.ModuleInfoView;

import java.util.ArrayList;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class Main1Activity extends BaseActivity implements ModuleInfoView {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private final int[] TITLES = new int[]{R.string.tab_home_txt, R.string.tab_my_txt};

    private final int[] IMAGES = new int[]{R.drawable.tab_home_selector, R.drawable.tab_my_selector};

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    private MyFragmentAdapter adapter;

    ModuleInfoPresenterImp moduleInfoPresenterImp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main1;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        Main1ActivityPermissionsDispatcher.showRecordWithCheck(this);

        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new MyFragment());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Main1ActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRecord() {
        //ToastUtils.showLong("允许使用存储权限");
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onRecordDenied() {
        Toast.makeText(this, R.string.permission_storage_denied, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForRecord(PermissionRequest request) {
        showRationaleDialog(R.string.permission_storage_rationale, request);
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onCameraNeverAskAgain() {
        Toast.makeText(this, R.string.permission_storage_never_ask_again, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initViews() {
        setTabs(tabLayout, this.getLayoutInflater(), TITLES, IMAGES);
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //绑定tab点击事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                Logger.i("tab pos --->" + pos);
                viewPager.setCurrentItem(pos);
                if (pos == 0) {
                    if (mFragmentList.get(pos) instanceof HomeFragment) {
                        ((HomeFragment) mFragmentList.get(pos)).setTopViewBgColor();
                    }
                }
                if (pos == 1) {
                    if (mFragmentList.get(pos) instanceof MyFragment) {
                        ((MyFragment) mFragmentList.get(pos)).setTopViewBgColor();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        moduleInfoPresenterImp = new ModuleInfoPresenterImp(this, this);
        moduleInfoPresenterImp.getModuleList();
    }

    public void setTabs(TabLayout tabLayout, LayoutInflater layoutInflater, int[] titles, int[] images) {
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.tab_custom, null);
            tab.setCustomView(view);
            TextView tabText = view.findViewById(R.id.tv_tab);
            tabText.setText(titles[i]);
            ImageView tabImage = view.findViewById(R.id.iv_tab);
            tabImage.setImageResource(images[i]);
            tabLayout.addTab(tab);
        }
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(Main1Activity.this, null);
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), 0);
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(ModuleInfoRet tData) {
        Logger.i("edit bar list--->" + JSON.toJSONString(tData));
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null) {
                App.getApp().setModuleInfoWrapper(tData.getData());
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }
}
