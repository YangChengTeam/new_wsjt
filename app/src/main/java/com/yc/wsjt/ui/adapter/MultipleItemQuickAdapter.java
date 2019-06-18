package com.yc.wsjt.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.WeiXinPayInfo;

import java.util.List;

/**
 *
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<WeiXinPayInfo, BaseViewHolder> {

    public MultipleItemQuickAdapter(Context context, List<WeiXinPayInfo> list) {
        super(list);
        addItemType(WeiXinPayInfo.PERSON, R.layout.pay_person_item);
        addItemType(WeiXinPayInfo.MERCHANT, R.layout.pay_merchant_item);
        addItemType(WeiXinPayInfo.RECEIVE_CODE, R.layout.pay_receive_code_item);
        addItemType(WeiXinPayInfo.MONEY_START, R.layout.pay_money_start_item);
        addItemType(WeiXinPayInfo.MONEY_END, R.layout.pay_money_end_item);
        addItemType(WeiXinPayInfo.RECEIVE_MERCHANT, R.layout.pay_receive_merchant_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeiXinPayInfo item) {
        switch (helper.getItemViewType()) {
            case WeiXinPayInfo.PERSON:
                helper.setText(R.id.tv_top_title, "支付的标题测试");
                break;
            case WeiXinPayInfo.MERCHANT:
                helper.setText(R.id.tv_top_title, "商户名称");
                break;
            case WeiXinPayInfo.RECEIVE_CODE:
                helper.setText(R.id.tv_top_title, "收款到账通知");
                break;
            default:
                break;
        }
    }

}
