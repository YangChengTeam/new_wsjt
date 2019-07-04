package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yc.wsjt.R;

/**
 * 特别说明
 */
public class ExplainDialog extends Dialog {

    private Context mContext;

    LinearLayout mConfigLayout;

    LinearLayout mCancelLayout;

    public ExplainListener explainListener;

    public interface ExplainListener {
        void configExplain();

        void signOut();
    }

    public void setExplainListener(ExplainListener explainListener) {
        this.explainListener = explainListener;
    }

    public ExplainDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ExplainDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explain_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mConfigLayout = findViewById(R.id.layout_config);
        mCancelLayout = findViewById(R.id.layout_cancel);

        mConfigLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explainListener.configExplain();
                dismiss();
            }
        });

        mCancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explainListener.signOut();
                dismiss();
            }
        });
    }

}