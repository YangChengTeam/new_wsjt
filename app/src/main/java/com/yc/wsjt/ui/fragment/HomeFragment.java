package com.yc.wsjt.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.BannerInfo;
import com.yc.wsjt.bean.HomeDateInfoRet;
import com.yc.wsjt.bean.QuickInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.HomeInfoPresenterImp;
import com.yc.wsjt.ui.activity.AdActivity;
import com.yc.wsjt.ui.activity.AlipaydanliaoActivity;
import com.yc.wsjt.ui.activity.BaseActivity;
import com.yc.wsjt.ui.activity.ChatVideoSetActivity;
import com.yc.wsjt.ui.activity.ChatVoiceSetActivity;
import com.yc.wsjt.ui.activity.ExtractSetActivity;
import com.yc.wsjt.ui.activity.MoneyDetailListActivity;
import com.yc.wsjt.ui.activity.MyWalletActivity;
import com.yc.wsjt.ui.activity.NewFriendListActivity;
import com.yc.wsjt.ui.activity.PaySuccessSetActivity;
import com.yc.wsjt.ui.activity.QQdanliaoActivity;
import com.yc.wsjt.ui.activity.QuickBarEditActivity;
import com.yc.wsjt.ui.activity.RedPackageActivity;
import com.yc.wsjt.ui.activity.TransferActivity;
import com.yc.wsjt.ui.activity.WeiXinBillActivity;
import com.yc.wsjt.ui.activity.WeiXinCircleSetActivity;
import com.yc.wsjt.ui.activity.WeiXinHomeActivity;
import com.yc.wsjt.ui.activity.WeiXinModuleActivity;
import com.yc.wsjt.ui.activity.WeiXinMoneyActivity;
import com.yc.wsjt.ui.activity.WeiXinPayListActivity;
import com.yc.wsjt.ui.activity.WeixindanliaoActivity;
import com.yc.wsjt.ui.activity.WeixinqunliaoActivity;
import com.yc.wsjt.ui.adapter.QuickAdapter;
import com.yc.wsjt.ui.custom.GlideImageLoader;
import com.yc.wsjt.ui.custom.LoginDialog;
import com.yc.wsjt.view.HomeInfoView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import permissions.dispatcher.PermissionRequest;

/**
 * Created by admin on 2017/4/20.
 */

public class HomeFragment extends BaseFragment implements HomeInfoView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.home_swipe)
    SwipeRefreshLayout mRefreshLayout;

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

    private List<BannerInfo> bannerInfos;

    BaseDownloadTask task;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initVars() {

    }

    @Override
    public void initViews() {
        mRefreshLayout.setOnRefreshListener(this);
        //设置进度View样式的大小，只有两个值DEFAULT和LARGE
        //设置进度View下拉的起始点和结束点，scale 是指设置是否需要放大或者缩小动画
        mRefreshLayout.setProgressViewOffset(true, -0, 200);
        //设置进度View下拉的结束点，scale 是指设置是否需要放大或者缩小动画
        mRefreshLayout.setProgressViewEndTarget(true, 180);
        //设置进度View的组合颜色，在手指上下滑时使用第一个颜色，在刷新中，会一个个颜色进行切换
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorPrimary), Color.RED, Color.YELLOW, Color.BLUE);

        //设置触发刷新的距离
        mRefreshLayout.setDistanceToTriggerSync(200);
        //如果child是自己自定义的view，可以通过这个回调，告诉mSwipeRefreshLayoutchild是否可以滑动
        mRefreshLayout.setOnChildScrollUpCallback(null);

        setTopViewBgColor();
        //setTvTitleBackgroundColor(Color.TRANSPARENT);
        FileDownloader.setup(getActivity());

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

        //登录过，并且是VIP用户
        if (App.getApp().isLogin && App.getApp().mUserInfo != null && App.getApp().mUserInfo.getStatus() > 1) {
            App.getApp().setOpenVip(true);
        }

        quickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == quickAdapter.getData().size() - 1) {
                    Intent intent = new Intent(getActivity(), QuickBarEditActivity.class);
                    startActivity(intent);
                } else {
                    if (StringUtils.isEmpty(quickAdapter.getData().get(position).getImg())) {
                        if (!App.getApp().isLogin) {
                            if (loginDialog != null && !loginDialog.isShowing()) {
                                loginDialog.show();
                            }
                            return;
                        }

                        boolean isUse = true;
                        //判断是否是VIP用户，以及功能模块是否是免费使用
                        if (quickAdapter.getData().get(position).getVip() == 1 && !App.getApp().isOpenVip()) {
                            isUse = false;
                        }

                        int mid = quickAdapter.getData().get(position).getId();

                        if (mid == 1) {
                            Intent intent = new Intent(getActivity(), WeixindanliaoActivity.class);
                            startActivity(intent);
                        }
                        if (mid == 2) {
                            Intent intent = new Intent(getActivity(), WeiXinMoneyActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 4) {
                            Intent intent = new Intent(getActivity(), WeixinqunliaoActivity.class);
                            startActivity(intent);
                        }

                        if (mid == 5) {
                            Intent intent = new Intent(getActivity(), RedPackageActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 6) {
                            Intent intent = new Intent(getActivity(), TransferActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 7) {
                            Intent intent = new Intent(getActivity(), ExtractSetActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 8) {
                            Intent intent = new Intent(getActivity(), MyWalletActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 9) {
                            Intent intent = new Intent(getActivity(), MoneyDetailListActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 10) {
                            Intent intent = new Intent(getActivity(), NewFriendListActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 11) {
                            Intent intent = new Intent(getActivity(), WeiXinCircleSetActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 12) {
                            Intent intent = new Intent(getActivity(), ChatVideoSetActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 13) {
                            Intent intent = new Intent(getActivity(), ChatVoiceSetActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 14) {
                            Intent intent = new Intent(getActivity(), PaySuccessSetActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 15) {
                            Intent intent = new Intent(getActivity(), WeiXinPayListActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 16) {
                            Intent intent = new Intent(getActivity(), WeiXinBillActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        if (mid == 17) {
                            Intent intent = new Intent(getActivity(), WeiXinHomeActivity.class);
                            if (quickAdapter.getData().get(position).getVip() == 1) {
                                intent.putExtra("is_use", isUse);
                            }
                            startActivity(intent);
                        }
                        
                    } else {
                        ToastUtils.showLong("广告类型--->");
                        int type = quickAdapter.getData().get(position).getType();
                        if (type == 1) {
                            String downUrl = quickAdapter.getData().get(position).getVal();
                            downAppFile(downUrl);
                        }

                        if (type == 2) {
                            Intent intent = new Intent(getActivity(), AdActivity.class);
                            intent.putExtra("ad_url", quickAdapter.getData().get(position).getVal());
                            intent.putExtra("ad_title", quickAdapter.getData().get(position).getName());
                            startActivity(intent);
                        }

                        if (type == 3) {
                            ToastUtils.showLong("打开小程序");
                        }
                    }
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
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (bannerInfos != null && bannerInfos.size() > 0) {
                    if (bannerInfos.get(position).getType() == 1) {
                        //下载
                        String downUrl = bannerInfos.get(position).getVal();
                        downAppFile(downUrl);
                    }
                    if (bannerInfos.get(position).getType() == 2) {
                        //网页
                        Intent intent = new Intent(getActivity(), AdActivity.class);
                        intent.putExtra("ad_url", bannerInfos.get(position).getVal());
                        intent.putExtra("ad_title", bannerInfos.get(position).getName());
                        startActivity(intent);
                    }
                    if (bannerInfos.get(position).getType() == 3) {
                        //小程序
                        ToastUtils.showLong("打开小程序");
                    }
                }
            }
        });
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
                List<QuickInfo> list = ((BaseActivity) getActivity()).mAppDatabase.quickInfoDao().loadQuickInfoInHome();
                if (list != null) {
                    quickAdapter.setNewData(list);
                }
                addPlus();
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
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadDataSuccess(HomeDateInfoRet tData) {
        Logger.i("home result --->" + JSON.toJSONString(tData));
        mRefreshLayout.setRefreshing(false);
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null) {

                SPUtils.getInstance().put("new_help", tData.getData().getUrls().newHelp);
                SPUtils.getInstance().put("kf_help", tData.getData().getUrls().kfHelp);

                if (tData.getData().getBanner() != null && tData.getData().getBanner().size() > 0) {
                    bannerInfos = tData.getData().getBanner();
                    List<String> bannerList = new ArrayList<>();
                    for (int i = 0; i < bannerInfos.size(); i++) {
                        bannerList.add(Constants.BASE_IMAGE_URL + bannerInfos.get(i).getImg());
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
                        List<QuickInfo> tempList = new ArrayList<>();
                        for (int i = 0; i < tData.getData().getTool().size(); i++) {
                            QuickInfo temp = tData.getData().getTool().get(i);
                            temp.setAddDate(TimeUtils.date2Millis(new Date()));

                            //如果是广告组件，将ID增加
                            if (!StringUtils.isEmpty(temp.getImg())) {
                                temp.setId(10000 + temp.getId());
                            }
                            tempList.add(temp);
                        }

                        ((BaseActivity) getActivity()).mAppDatabase.quickInfoDao().insertList(tempList);
                        quickAdapter.setNewData(((BaseActivity) getActivity()).mAppDatabase.quickInfoDao().loadQuickInfoInHome());
                    }
                }
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        mRefreshLayout.setRefreshing(false);
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
        if (!App.getApp().isLogin) {
            if (loginDialog != null && !loginDialog.isShowing()) {
                loginDialog.show();
            }
            return;
        }

        switch (v.getId()) {
            case R.id.layout_weixin:
                Intent intent = new Intent(getActivity(), WeiXinModuleActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_alipay:
                Intent intent1 = new Intent(getActivity(), AlipaydanliaoActivity.class);
                intent1.putExtra("model_type", 1);
                startActivity(intent1);
                break;
            case R.id.layout_qq:
                Intent intent2 = new Intent(getActivity(), QQdanliaoActivity.class);
                intent2.putExtra("model_type", 2);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    public void downAppFile(String downUrl) {
        final String filePath = PathUtils.getExternalAppFilesPath() + "/new_app.apk";
        Logger.i("down app path --->" + filePath);

        task = FileDownloader.getImpl().create(downUrl)
                .setPath(filePath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Toasty.normal(getActivity(), "正在下载，请稍后...").show();
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        ToastUtils.showLong("下载完成");
                        //install(filePath);
                        AppUtils.installApp(filePath);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                });

        task.start();
    }

    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        SPUtils.getInstance().put(Constants.HOME_IS_FIRST_LOAD, true);
        homeInfoPresenterImp.getHomeList();
    }
}
