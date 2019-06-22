package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.PayInfo;
import com.yc.wsjt.bean.WeiXinPayInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.MultipleItemQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangdinghui on 2019/5/29.
 * <p>
 * 微信支付-生成预览
 */
public class WeiXinPayListPreActivity extends BaseActivity {

    @BindView(R.id.pay_list)
    SwipeRecyclerView mPayListView;

    @BindView(R.id.iv_pay_bg)
    ImageView mPayBgIv;

    MultipleItemQuickAdapter multipleItemQuickAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_list_show;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);

        multipleItemQuickAdapter = new MultipleItemQuickAdapter(this, null);
        mPayListView.setLayoutManager(new LinearLayoutManager(this));
        mPayListView.setAdapter(multipleItemQuickAdapter);

        View footView = new View(this);
        footView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(24)));
        multipleItemQuickAdapter.addFooterView(footView);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String payBg = SPUtils.getInstance().getString("pay_bg", "");
        if (!StringUtils.isEmpty(payBg)) {
            Glide.with(this).load(payBg).into(mPayBgIv);
        }

        mAppDatabase.payInfoDao()
                .loadPayInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PayInfo>>() {
                    @Override
                    public void accept(List<PayInfo> payInfos) {
                        if (payInfos != null) {
                            List<WeiXinPayInfo> tempList = new ArrayList<>();
                            for (int i = 0; i < payInfos.size(); i++) {
                                PayInfo payInfo = payInfos.get(i);
                                WeiXinPayInfo weiXinPayInfo = new WeiXinPayInfo(payInfo.getPayType());
                                weiXinPayInfo.setPayInfo(payInfo);
                                tempList.add(weiXinPayInfo);
                            }
                            multipleItemQuickAdapter.setNewData(tempList);
                        }
                    }
                });
    }
}
