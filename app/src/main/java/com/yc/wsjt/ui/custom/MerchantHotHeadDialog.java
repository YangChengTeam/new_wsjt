package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MerchantInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.ui.adapter.MerchantHeadAdapter;

import java.util.ArrayList;
import java.util.List;


public class MerchantHotHeadDialog extends Dialog {

    private Context mContext;

    RecyclerView merchantHeadListView;

    MerchantHeadAdapter merchantHeadAdapter;

    public MerchantHotHeadDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public MerchantHotHeadDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_merchant_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {

        //聊天的类型
        List<MerchantInfo> headList = new ArrayList<>();
        for (int i = 0; i < Constants.typeImages.length - 5; i++) {
            MerchantInfo merchantInfo = new MerchantInfo();
            merchantInfo.setMerchantImg(Constants.typeImages[i]);
            merchantInfo.setMerchantName(mContext.getResources().getString(Constants.chatTypeNames[i]));
            headList.add(merchantInfo);
        }

        merchantHeadListView = findViewById(R.id.merchant_list);
        merchantHeadAdapter = new MerchantHeadAdapter(mContext, headList);
        merchantHeadListView.addItemDecoration(new NormalDecoration(ContextCompat.getColor(mContext, R.color.line_color), SizeUtils.dp2px(0.5f)));
        merchantHeadListView.setLayoutManager(new LinearLayoutManager(mContext));
        merchantHeadListView.setAdapter(merchantHeadAdapter);

        merchantHeadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                dismiss();
            }
        });
    }
}