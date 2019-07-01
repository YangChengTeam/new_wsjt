package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.SizeUtils;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MoneyDetail;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.MoneyDetailListPreAdapter;
import com.yc.wsjt.ui.custom.NormalDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangdinghui on 2019/5/27.
 */
public class MoneyDetailListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_add_data)
    Button mAddDataBtn;

    @BindView(R.id.btn_pre_show)
    Button mQueryDataBtn;

    @BindView(R.id.money_detail_list)
    SwipeRecyclerView mDetailListView;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    MoneyDetailListPreAdapter moneyDetailListPreAdapter;

    private boolean isUse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_money_detail_list;
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
        mTitleTv.setText("微信零钱设置");

        moneyDetailListPreAdapter = new MoneyDetailListPreAdapter(this, null, 0);
        mDetailListView.setLayoutManager(new LinearLayoutManager(this));
        mDetailListView.addItemDecoration(new NormalDecoration(ContextCompat.getColor(this, R.color.border_line_color), SizeUtils.dp2px(0.5f)));
        mDetailListView.setAdapter(moneyDetailListPreAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAppDatabase.moneyDetailDao()
                .loadMoneyMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MoneyDetail>>() {
                    @Override
                    public void accept(List<MoneyDetail> moneyDetails) {
                        if (moneyDetails != null) {
                            moneyDetailListPreAdapter.setNewData(moneyDetails);
                            mNoDataLayout.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isUse = bundle.getBoolean("is_use", false);
        }
    }

    @OnClick(R.id.btn_add_data)
    void add() {
        Intent intent = new Intent(this, AddMoneyDetailActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_pre_show)
    void preShow() {
        Intent intent = new Intent(this, MoneyListPreActivity.class);
        intent.putExtra("is_use", isUse);
        startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
