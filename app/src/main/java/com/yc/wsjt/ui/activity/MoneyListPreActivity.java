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
        List<MoneyDetail> list = new ArrayList<>();
        MoneyDetail moneyDetailInfo = new MoneyDetail();
        moneyDetailInfo.setMoneyTitle("微信面对面转账");
        moneyDetailInfo.setAddTime("2019-05-12 12:23:21");
        moneyDetailInfo.setMoney("-123");

        list.add(moneyDetailInfo);
        list.add(moneyDetailInfo);
        list.add(moneyDetailInfo);
        list.add(moneyDetailInfo);
        list.add(moneyDetailInfo);

        moneyDetailListPreAdapter = new MoneyDetailListPreAdapter(this, list, 0);
        mDetailListView.setLayoutManager(new LinearLayoutManager(this));
        mDetailListView.addItemDecoration(new NormalDecoration(ContextCompat.getColor(this, R.color.border_line_color), SizeUtils.dp2px(0.5f)));
        mDetailListView.setAdapter(moneyDetailListPreAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
