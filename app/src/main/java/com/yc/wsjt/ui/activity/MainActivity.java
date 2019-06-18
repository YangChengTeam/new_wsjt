package com.yc.wsjt.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.HomeDateInfoRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.HomeInfoPresenterImp;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.QuickAdapter;
import com.yc.wsjt.view.HomeInfoView;
import com.youth.banner.Banner;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by zhangdinghui on 2019/4/19.
 */

@RuntimePermissions
public class MainActivity extends BaseActivity implements HomeInfoView, View.OnClickListener {

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        MainActivityPermissionsDispatcher.showRecordWithCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
        quickAdapter = new QuickAdapter(this, null, ScreenUtils.getScreenWidth() / 3);
        quickListView.setLayoutManager(new GridLayoutManager(this, 3));
        quickListView.setAdapter(quickAdapter);

        //头部view
        headView = LayoutInflater.from(this).inflate(R.layout.home_head_view, null);
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
                Intent intent = new Intent(MainActivity.this, WeixindanliaoActivity.class);
                startActivity(intent);
            }
        });

        mEditTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuickBarEditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        homeInfoPresenterImp = new HomeInfoPresenterImp(this, this);
        homeInfoPresenterImp.getHomeList();
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
//            if (tData.getData() != null) {
//                if (tData.getData().getBanner() != null && tData.getData().getBanner().size() > 0) {
//                    List<String> bannerList = new ArrayList<>();
//                    for (int i = 0; i < tData.getData().getBanner().size(); i++) {
//                        bannerList.add(Constants.BASE_IMAGE_URL + tData.getData().getBanner().get(i).getImg());
//                    }
//                    // 设置数据
//                    //设置图片加载器
//                    mBanner.setImageLoader(new GlideImageLoader());
//                    //设置图片集合
//                    mBanner.setImages(bannerList);
//                    //banner设置方法全部调用完毕时最后调用
//                    mBanner.start();
//                }
//                if (tData.getData().getTool() != null && tData.getData().getTool().size() > 0) {
//                    quickAdapter.setNewData(tData.getData().getTool());
//                    mAppDatabase.quickInfoDao().insertList(tData.getData().getTool());
//                }
//            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_weixin:
                Intent intent = new Intent(this, WeiXinModuleActivity.class);
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
