package com.yc.wsjt.ui.activity;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MoneyDetail;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.MoneyDetailListPreAdapter;
import com.yc.wsjt.ui.custom.NormalDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangdinghui on 2019/5/27.
 * 零钱明细
 */
public class MoneyListPreActivity extends BaseActivity {

    @BindView(R.id.detail_list_view)
    RecyclerView mDetailListView;

    MoneyDetailListPreAdapter moneyDetailListPreAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_money_list_pre;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initViews() {
        moneyDetailListPreAdapter = new MoneyDetailListPreAdapter(this, null, 0);
        mDetailListView.setLayoutManager(new LinearLayoutManager(this));
        mDetailListView.addItemDecoration(new NormalDecoration(ContextCompat.getColor(this, R.color.border_line_color), SizeUtils.dp2px(0.5f)));
        mDetailListView.setAdapter(moneyDetailListPreAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mAppDatabase.moneyDetailDao()
                .loadMoneyMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MoneyDetail>>() {
                    @Override
                    public void accept(List<MoneyDetail> moneyDetails) {
                        if (moneyDetails != null) {
                            moneyDetailListPreAdapter.setNewData(moneyDetails);
                        }
                    }
                });
    }
}
