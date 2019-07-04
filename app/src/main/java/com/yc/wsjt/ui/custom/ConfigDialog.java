package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yc.wsjt.R;

public class ConfigDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private LinearLayout mConfigLayout;

    private LinearLayout mCancelLayout;

    private ConfigDeleteListener configDeleteListener;

    public interface ConfigDeleteListener {
        void configDelete();

        void cancelDelete();
    }

    public void setConfigDeleteListener(ConfigDeleteListener configDeleteListener) {
        this.configDeleteListener = configDeleteListener;
    }

    public ConfigDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ConfigDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mConfigLayout = findViewById(R.id.layout_config);
        mCancelLayout = findViewById(R.id.layout_cancel);
        mConfigLayout.setOnClickListener(this);
        mCancelLayout.setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_config:
                configDeleteListener.configDelete();
                this.dismiss();
                break;
            case R.id.layout_cancel:
                configDeleteListener.cancelDelete();
                this.dismiss();
                break;
            case R.id.iv_close:
                this.dismiss();
                break;
        }
    }
}