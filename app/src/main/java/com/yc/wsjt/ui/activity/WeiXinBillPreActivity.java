package com.yc.wsjt.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.gavin.com.library.PowerfulStickyDecoration;
import com.gavin.com.library.listener.OnGroupClickListener;
import com.gavin.com.library.listener.PowerGroupListener;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.BillInfo;
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
public class WeiXinBillPreActivity extends BaseActivity implements VipPayTypeDialog.PayListener {

    @BindView(R.id.bill_list)
    RecyclerView mBillListView;

    BillListAdapter billListAdapter;

    private boolean isUse;

    PowerfulStickyDecoration decoration;

    List<BillInfo> totlaList;

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
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initViews() {
        vipPayTypeDialog.setPayListener(this);
        if (mAppDatabase.billInfoDao().getBillList() != null && mAppDatabase.billInfoDao().getBillList().size() > 0) {
            totlaList = mAppDatabase.billInfoDao().getBillList();
        } else {
            totlaList = new ArrayList<>();
        }

        PowerGroupListener listener = new PowerGroupListener() {
            @Override
            public String getGroupName(int position) {
                //获取组名，用于判断是否是同一组
                if (totlaList.size() > position) {
                    Logger.i("pos--->" + totlaList.get(position).getYearMonth());
                    return totlaList.get(position).getYearMonth();
                }
                return null;
            }

            @Override
            public View getGroupView(int position) {
                //获取自定定义的组View
                if (totlaList.size() > position) {

                    //Logger.i("pos--->" + position + " --- " + totlaList.get(position).getYearMonth() + "---" + totlaList.get(position).getBillMoney());

                    View view = getLayoutInflater().inflate(R.layout.bill_sticky_view, null, false);
                    ((TextView) view.findViewById(R.id.tv_bill_month)).setText(totlaList.get(position).getYearMonth());

                    double[] moneys = getMonthTotal(totlaList.get(position).getYearMonth());
                    if (moneys != null && moneys.length == 2) {
                        ((TextView) view.findViewById(R.id.tv_turn_out_money)).setText("¥" + moneys[0]);
                        ((TextView) view.findViewById(R.id.tv_receive_money)).setText("¥" + moneys[1]);
                    }
                    return view;
                } else {
                    return null;
                }
            }
        };
        decoration = PowerfulStickyDecoration.Builder
                .init(listener)
                .setGroupHeight(SizeUtils.dp2px(80))
                .setGroupBackground(Color.parseColor("#f6f6f6"))
                .setDivideColor(Color.parseColor("#ffffff"))
                .setDivideHeight(SizeUtils.dp2px(1))
                .setCacheEnable(true)
                .setHeaderCount(0)
                .setOnClickListener(new OnGroupClickListener() {
                    @Override
                    public void onClick(int position, int id) {
                        //Group点击事件
                        //String content = "onGroupClick --> " + dataList.get(position).getProvince() + "   id --> " + id;
                        //showToast(content);
                    }
                })
                .build();

        billListAdapter = new BillListAdapter(this, totlaList);
        mBillListView.setLayoutManager(new LinearLayoutManager(this));
        mBillListView.addItemDecoration(decoration);
        mBillListView.setAdapter(billListAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        isUse = bundle.getBoolean("is_use", false);

//        if (!isUse) {
//            if (openVipDialog != null && !openVipDialog.isShowing()) {
//                openVipDialog.show();
//                return;
//            }
//        }
    }

    public double[] getMonthTotal(String yearMonth) {
        double tempOut = 0;
        double tempReceive = 0;
        if (totlaList != null && totlaList.size() > 0) {
            for (int i = 0; i < totlaList.size(); i++) {
                if (totlaList.get(i).getYearMonth().endsWith(yearMonth)) {
                    if (totlaList.get(i).getBillType() == 1) {
                        tempReceive = tempReceive + Double.parseDouble(totlaList.get(i).getBillMoney());
                    } else {
                        tempOut = tempOut + Double.parseDouble(totlaList.get(i).getBillMoney());
                    }
                }
            }
        }
        double moneys[] = new double[2];
        moneys[0] = tempOut;
        moneys[1] = tempReceive;
        return moneys;
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
