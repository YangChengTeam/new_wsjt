package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yc.wsjt.R;


public class OpenVipDialog extends Dialog {

    private Context mContext;

    ImageView mCloseIv;

    Button mCommentBtn;

    Button mOpenVipBtn;

    public interface VipListener {
        void addComment();

        void openVip();

        void closeOpenVip();
    }

    public VipListener vipListener;

    public void setVipListener(VipListener vipListener) {
        this.vipListener = vipListener;
    }

    public OpenVipDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public OpenVipDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_vip_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mCloseIv = findViewById(R.id.iv_close);
        mCommentBtn = findViewById(R.id.btn_comment);
        mOpenVipBtn = findViewById(R.id.btn_open_vip);

        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                vipListener.addComment();
            }
        });

        mOpenVipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                vipListener.openVip();
            }
        });

        mCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                vipListener.closeOpenVip();
            }
        });
    }
}