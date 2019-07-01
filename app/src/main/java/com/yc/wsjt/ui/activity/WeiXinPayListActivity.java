package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.PayInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.PayTypeInfoAdapter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.custom.NormalDecoration;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangdinghui on 2019/5/27.
 */
public class WeiXinPayListActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_add_data)
    Button mAddDataBtn;

    @BindView(R.id.btn_pre_show)
    Button mQueryDataBtn;

    @BindView(R.id.pay_list_view)
    SwipeRecyclerView payListView;

    @BindView(R.id.layout_no_data)
    LinearLayout mNoDataLayout;

    @BindView(R.id.iv_pay_bg)
    ImageView mPayBgIv;

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mVoucherLayout;

    LinearLayout mVoucherMerchantLayout;

    LinearLayout mReceiveCodeLayout;

    LinearLayout mMoneyStartLayout;

    LinearLayout mMoneyEndLayout;

    LinearLayout mReceiveByMerchantLayout;

    PayTypeInfoAdapter payTypeInfoAdapter;

    private boolean isUse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_list;
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
        mTitleTv.setText("微信支付");

        payTypeInfoAdapter = new PayTypeInfoAdapter(this, null);
        payListView.setLayoutManager(new LinearLayoutManager(this));
        payListView.addItemDecoration(new NormalDecoration(ContextCompat.getColor(this, R.color.line_color), 1));
        payListView.setAdapter(payTypeInfoAdapter);

        bottomSheetDialog = new BottomSheetDialog(this);
        View payTypeView = LayoutInflater.from(this).inflate(R.layout.add_wx_pay_type_view, null);

        mVoucherLayout = payTypeView.findViewById(R.id.layout_voucher);
        mVoucherMerchantLayout = payTypeView.findViewById(R.id.layout_voucher_merchant);
        mReceiveCodeLayout = payTypeView.findViewById(R.id.layout_receive_in_code);
        mMoneyStartLayout = payTypeView.findViewById(R.id.layout_money_start);
        mMoneyEndLayout = payTypeView.findViewById(R.id.layout_money_end);
        mReceiveByMerchantLayout = payTypeView.findViewById(R.id.layout_receive_by_merchant);

        mVoucherLayout.setOnClickListener(this);
        mVoucherMerchantLayout.setOnClickListener(this);
        mReceiveCodeLayout.setOnClickListener(this);
        mMoneyStartLayout.setOnClickListener(this);
        mMoneyEndLayout.setOnClickListener(this);
        mReceiveByMerchantLayout.setOnClickListener(this);

        bottomSheetDialog.setContentView(payTypeView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String payBg = SPUtils.getInstance().getString("pay_bg", "");
        if (!StringUtils.isEmpty(payBg)) {
            Glide.with(this).load(payBg).into(mPayBgIv);
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isUse = bundle.getBoolean("is_use", false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAppDatabase.payInfoDao()
                .loadPayInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PayInfo>>() {
                    @Override
                    public void accept(List<PayInfo> payInfos) {
                        if (payInfos != null) {
                            payTypeInfoAdapter.setNewData(payInfos);
                            mNoDataLayout.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @OnClick(R.id.btn_add_data)
    void add() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    @OnClick(R.id.btn_pre_show)
    void preShow() {
        Intent intent = new Intent(this, WeiXinPayListPreActivity.class);
        intent.putExtra("is_use", isUse);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
        switch (v.getId()) {
            case R.id.layout_voucher:
                Intent intent = new Intent(this, WxPayPersonActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_voucher_merchant:
                Intent intent1 = new Intent(this, WxPayMerchantActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_receive_in_code:
                Intent intent2 = new Intent(this, WxPayReceiveCodeActivity.class);
                startActivity(intent2);
                break;
            case R.id.layout_money_start:
                Intent intent3 = new Intent(this, WxPayStartActivity.class);
                startActivity(intent3);
                break;
            case R.id.layout_money_end:
                Intent intent4 = new Intent(this, WxPayEndActivity.class);
                startActivity(intent4);
                break;
            case R.id.layout_receive_by_merchant:
                Intent intent5 = new Intent(this, WxPayMerchantReceiveActivity.class);
                startActivity(intent5);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.layout_pay_bg)
    void payBg() {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .showSingleMediaType(true)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    Logger.i(JSONObject.toJSONString(Matisse.obtainPathResult(data)));
                    if (Matisse.obtainPathResult(data) != null && Matisse.obtainPathResult(data).size() > 0) {
                        SPUtils.getInstance().put("pay_bg", Matisse.obtainPathResult(data).get(0));
                        Glide.with(this).load(Matisse.obtainPathResult(data).get(0)).into(mPayBgIv);
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
