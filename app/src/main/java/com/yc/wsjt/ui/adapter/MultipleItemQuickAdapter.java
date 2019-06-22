package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.PayInfo;
import com.yc.wsjt.bean.WeiXinPayInfo;

import java.util.List;

/**
 *
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<WeiXinPayInfo, BaseViewHolder> {

    private String[] payTypeNames = {"微信支付凭证", "微信支付凭证", "收款到账通知", "零钱提现发起", "零钱提现到账", "微信支付凭证"};

    public MultipleItemQuickAdapter(Context context, List<WeiXinPayInfo> list) {
        super(list);
        addItemType(PayInfo.PERSON, R.layout.pay_person_item);
        addItemType(PayInfo.MERCHANT, R.layout.pay_merchant_item);
        addItemType(PayInfo.RECEIVE_CODE, R.layout.pay_receive_code_item);
        addItemType(PayInfo.MONEY_START, R.layout.pay_money_start_item);
        addItemType(PayInfo.MONEY_END, R.layout.pay_money_end_item);
        addItemType(PayInfo.RECEIVE_MERCHANT, R.layout.pay_receive_merchant_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeiXinPayInfo item) {
        PayInfo payInfo = item.getPayInfo();
        switch (helper.getItemViewType()) {
            case PayInfo.PERSON:
                helper.setText(R.id.tv_pay_time, payInfo.getPayDate())
                        .setText(R.id.tv_top_title, payTypeNames[payInfo.getPayType() - 1])
                        .setText(R.id.tv_pay_money, payInfo.getPayMoney())
                        .setText(R.id.tv_receive_user_name, payInfo.getReceiveUserName())
                        .setText(R.id.tv_remark, payInfo.getPayRemark());
                break;
            case PayInfo.MERCHANT:
                helper.setText(R.id.tv_pay_time, payInfo.getPayDate())
                        .setText(R.id.tv_merchant_name, payInfo.getMerchantName())
                        .setText(R.id.tv_pay_money, payInfo.getPayMoney());
                if (payInfo.getMerchantLocalHead() > 0) {
                    Glide.with(mContext).load(payInfo.getMerchantLocalHead()).into((ImageView) helper.getView(R.id.iv_merchant_head));
                } else {
                    Glide.with(mContext).load(payInfo.getMerchantHead()).into((ImageView) helper.getView(R.id.iv_merchant_head));
                }
                break;
            case PayInfo.RECEIVE_CODE:
                helper.setText(R.id.tv_pay_time, payInfo.getPayDate())
                        .setText(R.id.tv_pay_money, payInfo.getThisReceiveMoney())
                        .setText(R.id.tv_total_money_info, "今日第" + payInfo.getTotalCount() + "笔收款,共计¥" + payInfo.getTotalMoney());
                LinearLayout payRemarkLayout = helper.getView(R.id.layout_pay_remark);
                if (!StringUtils.isEmpty(payInfo.getPayRemark())) {
                    payRemarkLayout.setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_pay_remark, payInfo.getPayRemark());
                } else {
                    payRemarkLayout.setVisibility(View.GONE);
                }
                break;
            case PayInfo.MONEY_START:
                helper.setText(R.id.tv_pay_time, payInfo.getPayDate())
                        .setText(R.id.tv_start_date, payInfo.getPayDate())
                        .setText(R.id.tv_pay_money, payInfo.getPayMoney())
                        .setText(R.id.tv_bank_name, payInfo.getBankName())
                        .setText(R.id.tv_get_date, payInfo.getGetMoneyDate())
                        .setText(R.id.tv_arrival_date, payInfo.getReceiveMoneyDate());
                break;
            case PayInfo.MONEY_END:
                helper.setText(R.id.tv_pay_time, payInfo.getPayDate())
                        .setText(R.id.tv_start_date, payInfo.getPayDate())
                        .setText(R.id.tv_pay_money, payInfo.getPayMoney())
                        .setText(R.id.tv_bank_name, payInfo.getBankName())
                        .setText(R.id.tv_get_date, payInfo.getGetMoneyDate())
                        .setText(R.id.tv_arrival_date, payInfo.getReceiveMoneyDate());
                break;
            case PayInfo.RECEIVE_MERCHANT:
                helper.setText(R.id.tv_pay_time, payInfo.getPayDate())
                        .setText(R.id.tv_start_date, payInfo.getPayDate())
                        .setText(R.id.tv_pay_money, payInfo.getPayMoney())
                        .setText(R.id.tv_account, payInfo.getIntoAccount())
                        .setText(R.id.tv_merchant_name, payInfo.getMerchantName())
                        .setText(R.id.tv_remark, StringUtils.isEmpty(payInfo.getIntoMoneyRemark()) ? "" : payInfo.getIntoMoneyRemark());
            default:
                break;
        }
    }

}
