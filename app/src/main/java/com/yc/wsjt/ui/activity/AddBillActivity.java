package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/31.
 */
public class AddBillActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.iv_bill_icon)
    ImageView mBillIconIv;

    @BindView(R.id.tv_receive_money)
    TextView mReceiveTv;

    @BindView(R.id.tv_send_money)
    TextView mSendTv;

    BottomSheetDialog bottomSheetDialog;

    LinearLayout mRedLayout;

    LinearLayout mMoneyLayout;

    LinearLayout mReceiveCodeLayout;

    LinearLayout mRandomLayout;

    LinearLayout mCustomLayout;

    LinearLayout mCancelLayout;

    @BindView(R.id.layout_refund)
    RelativeLayout mRefundLayout;

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
    }

    @OnClick(R.id.iv_icon_refresh)
    void add() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    @OnClick(R.id.btn_config)
    void config() {
        finish();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.tv_receive_money)
    void receiveMoney() {
        mReceiveTv.setBackgroundResource(R.drawable.choose_type_selected);
        mReceiveTv.setTextColor(ContextCompat.getColor(this, R.color.white));

        mSendTv.setBackgroundResource(R.drawable.choose_type_normal);
        mSendTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        mRefundLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_send_money)
    void sendMoney() {
        mReceiveTv.setBackgroundResource(R.drawable.choose_type_normal);
        mReceiveTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        mSendTv.setBackgroundResource(R.drawable.choose_type_selected);
        mSendTv.setTextColor(ContextCompat.getColor(this, R.color.white));
        mRefundLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
        switch (v.getId()) {
            case R.id.layout_red:
                Glide.with(this).load(R.mipmap.bill_red_package).into(mBillIconIv);
                break;
            case R.id.layout_money:
                Glide.with(this).load(R.mipmap.bill_money).into(mBillIconIv);
                break;
            case R.id.layout_receive_in_code:
                Glide.with(this).load(R.mipmap.bill_receive_code).into(mBillIconIv);
                break;
            case R.id.layout_random_user:
                Glide.with(this).load(R.mipmap.avatar_def).into(mBillIconIv);
                break;
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
                    if (Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                        //Glide.with(context).load(Uri.fromFile(new File(Matisse.obtainPathResult(data).get(0)))).into(mChooseIv);
                        Logger.i("add image --->" + Matisse.obtainPathResult(data).get(0));
                        Glide.with(this).load(Matisse.obtainPathResult(data).get(0)).into(mBillIconIv);
                    }
                    break;
            }
        }
    }
}
