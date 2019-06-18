package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.yc.wsjt.R;
import com.yc.wsjt.util.StringUtils;


public class InputDialog extends Dialog {

    private Context mContext;

    EditText mWeixinNumberEt;

    LinearLayout mConfigLayout;

    LinearLayout mCancelLayout;

    public NumberListener numberListener;

    public interface NumberListener {
        void configNumber(String number);
    }

    public void setNumberListener(NumberListener numberListener) {
        this.numberListener = numberListener;
    }

    public InputDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public InputDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_dialog_view);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        mConfigLayout = findViewById(R.id.layout_config);
        mCancelLayout = findViewById(R.id.layout_cancel);
        mWeixinNumberEt = findViewById(R.id.et_weixin_number);

        mConfigLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (StringUtils.isEmpty(mWeixinNumberEt.getText())) {
                    ToastUtils.showLong("请输入微信号");
                    return;
                }

                numberListener.configNumber(mWeixinNumberEt.getText().toString());
                dismiss();
            }
        });

        mCancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}