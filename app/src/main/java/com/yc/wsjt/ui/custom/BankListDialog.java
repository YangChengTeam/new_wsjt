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
import com.yc.wsjt.bean.BankInfo;
import com.yc.wsjt.ui.adapter.BankListAdapter;

import java.util.ArrayList;
import java.util.List;


public class BankListDialog extends Dialog {

    private Context mContext;

    RecyclerView mBankListView;

    BankListAdapter bankListAdapter;
    String[] bankNames = {"农业银行", "中国银行", "建设银行", "招商银行", "民生银行", "交通银行", "华夏银行", "工商银行", "邮政储蓄银行", "浦发银行", "农村信用社"};

    int[] bankImages = {R.mipmap.nyyh_bank, R.mipmap.zgyh_bank, R.mipmap.jsyh_bank, R.mipmap.zsyh_bank,
            R.mipmap.msyh_bank, R.mipmap.jtyh_bank, R.mipmap.hxyh_bank, R.mipmap.gsyh_bank,
            R.mipmap.yzcxyh_bank, R.mipmap.pfyh_bank, R.mipmap.ncxys_bank};

    public interface BankInfoListener{
        void chooseBank(String bankName);
    }

    public BankInfoListener bankInfoListener;

    public void setBankInfoListener(BankInfoListener bankInfoListener) {
        this.bankInfoListener = bankInfoListener;
    }

    public BankListDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public BankListDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_bank_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {

        //聊天的类型
        List<BankInfo> bankList = new ArrayList<>();
        for (int i = 0; i < bankImages.length; i++) {
            BankInfo bankInfo = new BankInfo();
            bankInfo.setBankImg(bankImages[i]);
            bankInfo.setBankName(bankNames[i]);
            bankList.add(bankInfo);
        }

        mBankListView = findViewById(R.id.bank_list);
        bankListAdapter = new BankListAdapter(mContext, bankList);
        mBankListView.addItemDecoration(new NormalDecoration(ContextCompat.getColor(mContext, R.color.line_color), SizeUtils.dp2px(0.5f)));
        mBankListView.setLayoutManager(new LinearLayoutManager(mContext));
        mBankListView.setAdapter(bankListAdapter);

        bankListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bankInfoListener.chooseBank(bankNames[position]);
                dismiss();
            }
        });
    }
}