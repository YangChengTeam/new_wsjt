package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.yc.wsjt.R;
import com.yc.wsjt.util.StringUtils;


public class InputDialog extends Dialog {

    private Context mContext;

    private String mTitle;

    EditText mInputTxtEt;

    LinearLayout mConfigLayout;

    LinearLayout mCancelLayout;

    public InputTxtListener txtListener;

    private TextView mTitleTv;

    public interface InputTxtListener {
        void inputTxt(String inputTxt);
    }

    public void setTxtListener(InputTxtListener txtListener) {
        this.txtListener = txtListener;
    }

    public InputDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public InputDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public InputDialog(Context context, int themeResId, String title) {
        super(context, themeResId);
        this.mContext = context;
        this.mTitle = title;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
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
        mInputTxtEt = findViewById(R.id.et_input_txt);
        mTitleTv = findViewById(R.id.tv_title);
        mTitleTv.setText(mTitle);
        mConfigLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (StringUtils.isEmpty(mInputTxtEt.getText())) {
                    ToastUtils.showLong("请输入" + mTitle);
                    return;
                }

                txtListener.inputTxt(mInputTxtEt.getText().toString());
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