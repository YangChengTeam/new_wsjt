package com.yc.wsjt.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.PayInfo;

import java.util.List;

/**
 *
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<PayInfo, BaseViewHolder> {

    public MultipleItemQuickAdapter(Context context, List<PayInfo> list) {
        super(list);
        addItemType(PayInfo.PERSON, R.layout.pay_person_item);
        addItemType(PayInfo.MERCHANT, R.layout.pay_merchant_item);
        addItemType(PayInfo.RECEIVE_CODE, R.layout.pay_receive_code_item);
        addItemType(PayInfo.MONEY_START, R.layout.pay_money_start_item);
        addItemType(PayInfo.MONEY_END, R.layout.pay_money_end_item);
        addItemType(PayInfo.RECEIVE_MERCHANT, R.layout.pay_receive_merchant_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayInfo item) {
        switch (helper.getItemViewType()) {
            case PayInfo.PERSON:
                helper.setText(R.id.tv_top_title, "AAA");
                break;
            case PayInfo.MERCHANT:
                helper.setText(R.id.tv_top_title, "BBB");
                break;
            case PayInfo.RECEIVE_CODE:
                helper.setText(R.id.tv_top_title, "CCC");
                break;
            default:
                break;
        }
    }

}
