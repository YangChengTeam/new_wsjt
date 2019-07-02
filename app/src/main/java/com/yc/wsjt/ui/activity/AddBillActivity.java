package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kyleduo.switchbutton.SwitchButton;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.BillInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.CustomDateDialog;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/31.
 */
public class AddBillActivity extends BaseActivity implements View.OnClickListener, CustomDateDialog.DateSelectListener {

    public static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.iv_bill_icon)
    ImageView mBillIconIv;

    @BindView(R.id.et_bill_title)
    EditText mBillTitleEt;

    @BindView(R.id.et_money)
    EditText mMoneyEt;

    @BindView(R.id.tv_receive_money)
    TextView mReceiveTv;

    @BindView(R.id.tv_send_money)
    TextView mSendTv;

    @BindView(R.id.tv_bill_time)
    TextView mBillTimeTv;

    @BindView(R.id.sb_refund)
    SwitchButton mRefundSb;

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mRedLayout;

    LinearLayout mMoneyLayout;

    LinearLayout mReceiveCodeLayout;

    LinearLayout mRandomLayout;

    LinearLayout mCustomLayout;

    LinearLayout mCancelLayout;

    @BindView(R.id.layout_refund)
    RelativeLayout mRefundLayout;

    CustomDateDialog customDateDialog;

    private int billType = 1;

    private int billImage = R.mipmap.bill_red_package;

    private String billImageUrl;

    private String yearMonth;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bill;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        mTitleTv.setText("添加账单");
        String tempDate = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()));
        mBillTimeTv.setText(tempDate);

        if (!StringUtils.isEmpty(tempDate)) {
            String tempYear = tempDate.substring(0, 4);
            String tempMonth = tempDate.substring(5, 7);
            yearMonth = tempYear + "年" + Integer.parseInt(tempMonth) + "月";
        }
    }

    @Override
    protected void initViews() {
        bottomSheetDialog = new BottomSheetDialog(this);
        View billTypeView = LayoutInflater.from(this).inflate(R.layout.add_bill_type_view, null);

        mRedLayout = billTypeView.findViewById(R.id.layout_red);
        mMoneyLayout = billTypeView.findViewById(R.id.layout_money);
        mReceiveCodeLayout = billTypeView.findViewById(R.id.layout_receive_in_code);
        mRandomLayout = billTypeView.findViewById(R.id.layout_random_user);
        mCustomLayout = billTypeView.findViewById(R.id.layout_custom);
        mCancelLayout = billTypeView.findViewById(R.id.layout_view_cancel);

        mRedLayout.setOnClickListener(this);
        mMoneyLayout.setOnClickListener(this);
        mReceiveCodeLayout.setOnClickListener(this);
        mRandomLayout.setOnClickListener(this);
        mCustomLayout.setOnClickListener(this);
        mCancelLayout.setOnClickListener(this);

        bottomSheetDialog.setContentView(billTypeView);

        customDateDialog = new CustomDateDialog(this, R.style.date_dialog);
        customDateDialog.setDateSelectListener(this);
    }

    @OnClick(R.id.iv_icon_refresh)
    void add() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.tv_receive_money)
    void receiveMoney() {
        billType = 1;//收入
        mReceiveTv.setBackgroundResource(R.drawable.choose_type_selected);
        mReceiveTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mSendTv.setBackgroundResource(R.drawable.choose_type_normal);
        mSendTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        mRefundLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_send_money)
    void sendMoney() {
        billType = 2;//支出
        mReceiveTv.setBackgroundResource(R.drawable.choose_type_normal);
        mReceiveTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mSendTv.setBackgroundResource(R.drawable.choose_type_selected);
        mSendTv.setTextColor(ContextCompat.getColor(this, R.color.white));
        mRefundLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.iv_bill_right_icon)
    void timeSelect() {
        customDateDialog.show();
        //设置Dialog从窗体底部弹出
        customDateDialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams windowParams = customDateDialog.getWindow().getAttributes();
        windowParams.width = ScreenUtils.getScreenWidth();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDateDialog.getWindow().setAttributes(windowParams);
    }

    @OnClick(R.id.btn_config)
    void config() {
        if (StringUtils.isEmpty(mBillTitleEt.getText())) {
            ToastUtils.showLong("请输入账单标题");
            return;
        }

        if (StringUtils.isEmpty(mMoneyEt.getText())) {
            ToastUtils.showLong("请输入金额");
            return;
        }

        BillInfo billInfo = new BillInfo();
        if (billImage > 0) {
            billInfo.setBillImage(billImage);
        } else {
            billInfo.setBillImageUrl(billImageUrl);
        }
        billInfo.setBillTitle(mBillTitleEt.getText().toString());
        billInfo.setBillDate(mBillTimeTv.getText().toString());
        billInfo.setBillMoney(mMoneyEt.getText().toString());
        billInfo.setBillType(billType);
        billInfo.setRefund(mRefundSb.isSelected());
        billInfo.setBillImage(billImage);
        billInfo.setBillImageUrl(billImageUrl);
        billInfo.setYearMonth(StringUtils.isEmpty(yearMonth) ? "" : yearMonth);
        mAppDatabase.billInfoDao().insert(billInfo);

        finish();
    }

    @Override
    public void onClick(View v) {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
        switch (v.getId()) {
            case R.id.layout_red:
                billImage = R.mipmap.bill_red_package;
                Glide.with(this).load(R.mipmap.bill_red_package).into(mBillIconIv);
                break;
            case R.id.layout_money:
                billImage = R.mipmap.bill_money;
                Glide.with(this).load(R.mipmap.bill_money).into(mBillIconIv);
                break;
            case R.id.layout_receive_in_code:
                billImage = R.mipmap.bill_receive_code;
                Glide.with(this).load(R.mipmap.bill_receive_code).into(mBillIconIv);
                break;
            //case R.id.layout_random_user:
            //Glide.with(this).load(R.mipmap.avatar_def).into(mBillIconIv);
            //  break;
            case R.id.layout_custom:
                Matisse.from(this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .showSingleMediaType(true)
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.layout_view_cancel:

                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    Logger.i(JSONObject.toJSONString(Matisse.obtainPathResult(data)));
                    if (Matisse.obtainPathResult(data) != null && Matisse.obtainPathResult(data).size() > 0) {
                        billImageUrl = Matisse.obtainPathResult(data).get(0);
                        Glide.with(this).load(billImageUrl).into(mBillIconIv);
                    }
                    break;
            }
        }
    }

    @Override
    public void configDate(String selectDate) {
        Logger.i("select date --->" + selectDate);
        mBillTimeTv.setText(selectDate);
        if (!StringUtils.isEmpty(selectDate)) {
            String tempYear = selectDate.substring(0, 4);
            String tempMonth = selectDate.substring(5, 7);
            yearMonth = tempYear + "年" + Integer.parseInt(tempMonth) + "月";
        }
    }
}
