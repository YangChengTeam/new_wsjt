package com.yc.wsjt.ui.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.BillListAdapter;
import com.yc.wsjt.ui.custom.VipPayTypeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/6/1.
 */
public class WeiXinBillPreActivity extends BaseActivity implements VipPayTypeDialog.PayListener{

    @BindView(R.id.bill_list)
    RecyclerView mBillListView;

    BillListAdapter billListAdapter;

    private boolean isUse;

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
        vipPayTypeDialog.setPayListener(this);
        
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
        Bundle bundle = getIntent().getExtras();
        isUse = bundle.getBoolean("is_use", false);

        if (!isUse) {
            if (openVipDialog != null && !openVipDialog.isShowing()) {
                openVipDialog.show();
                return;
            }
        }
    }

    @Override
    public void addComment() {
        super.addComment();
    }

    @Override
    public void closeOpenVip() {
        super.closeOpenVip();
        finish();
    }

    @Override
    public void pay() {
        Logger.i("打开支付--->");
    }

    @Override
    public void payClose() {
        Logger.i("支付界面关闭--->");
        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
