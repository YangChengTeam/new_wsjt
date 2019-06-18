package com.yc.wsjt.ui.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.WeiXinPayInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.MultipleItemQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangdinghui on 2019/5/29.
 * <p>
 * 微信支付-生成预览
 */
public class WeiXinPayListPreActivity extends BaseActivity {

    @BindView(R.id.pay_list)
    SwipeRecyclerView mPayListView;

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
        List<WeiXinPayInfo> list = new ArrayList<>();
        WeiXinPayInfo weiXinPayInfo = new WeiXinPayInfo(WeiXinPayInfo.PERSON);
        WeiXinPayInfo weiXinPayInfo1 = new WeiXinPayInfo(WeiXinPayInfo.MERCHANT);
        WeiXinPayInfo weiXinPayInfo2 = new WeiXinPayInfo(WeiXinPayInfo.RECEIVE_CODE);

        WeiXinPayInfo weiXinPayInfo3 = new WeiXinPayInfo(WeiXinPayInfo.MONEY_START);
        WeiXinPayInfo weiXinPayInfo4 = new WeiXinPayInfo(WeiXinPayInfo.MONEY_END);
        WeiXinPayInfo weiXinPayInfo5 = new WeiXinPayInfo(WeiXinPayInfo.RECEIVE_MERCHANT);

        list.add(weiXinPayInfo);
        list.add(weiXinPayInfo1);
        list.add(weiXinPayInfo2);
        list.add(weiXinPayInfo3);
        list.add(weiXinPayInfo4);
        list.add(weiXinPayInfo5);

        multipleItemQuickAdapter = new MultipleItemQuickAdapter(this,list);
        mPayListView.setLayoutManager(new LinearLayoutManager(this));
        mPayListView.setAdapter(multipleItemQuickAdapter);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
