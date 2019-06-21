package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.PayInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.MultipleItemQuickAdapter;

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
        multipleItemQuickAdapter = new MultipleItemQuickAdapter(this, null);
        mPayListView.setLayoutManager(new LinearLayoutManager(this));
        mPayListView.setAdapter(multipleItemQuickAdapter);
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
                            multipleItemQuickAdapter.setNewData(payInfos);
                        }
                    }
                });
    }
}
