package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yc.wsjt.R;

/**
 * 温馨提示
 */
public class ProposeDialog extends Dialog {

    private Context mContext;

    LinearLayout mContinueLayout;

    LinearLayout mCancelLayout;

    public ProposeListener proposeListener;

    public interface ProposeListener {
        void continueUse();

        void cancelUse();
    }

    public void setProposeListener(ProposeListener proposeListener) {
        this.proposeListener = proposeListener;
    }

    public ProposeDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ProposeDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.propose_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mContinueLayout = findViewById(R.id.layout_continue_use);
        mCancelLayout = findViewById(R.id.layout_cancel);

        mContinueLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proposeListener.continueUse();
                dismiss();
            }
        });

        mCancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proposeListener.cancelUse();
                dismiss();
            }
        });
    }

}