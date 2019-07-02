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
import com.yc.wsjt.ui.activity.QQdanliaoActivity;
import com.yc.wsjt.ui.activity.QuickBarEditActivity;
import com.yc.wsjt.ui.activity.WeiXinModuleActivity;
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
                        int mid = quickAdapter.getData().get(position).getId();
                        if (mid == 1) {
                            Intent intent = new Intent(getActivity(), WeixindanliaoActivity.class);
                            startActivity(intent);
                        }

                        if (mid == 4) {
                            Intent intent = new Intent(getActivity(), WeixinqunliaoActivity.class);
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
}
