package com.yc.wsjt.ui.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.BillListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangdinghui on 2019/6/1.
 */
public class WeiXinBillPreActivity extends BaseActivity {

    @BindView(R.id.bill_list)
    RecyclerView mBillListView;

    BillListAdapter billListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bill_show;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {

    }

    @Override
    protected void initViews() {
        List<String> billList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            billList.add("" + i);
        }

        billListAdapter = new BillListAdapter(this, billList);
        mBillListView.setLayoutManager(new LinearLayoutManager(this));
        mBillListView.setAdapter(billListAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
