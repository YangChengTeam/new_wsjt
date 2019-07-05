package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.ui.activity.ChooseRoleActivity;
import com.yc.wsjt.ui.activity.EditRoleActivity;


public class SettingRoleDialog extends Dialog {

    private Context mContext;

    TextView mChangeRoleTv;

    TextView mEditRoleTv;

    private int type;

    private int modelType;

    public void setType(int type) {
        this.type = type;
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public SettingRoleDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public SettingRoleDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_role_dialog_view);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        mChangeRoleTv = findViewById(R.id.tv_change_role);
        mEditRoleTv = findViewById(R.id.tv_edit_role);
        mChangeRoleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Intent intent = new Intent(mContext, ChooseRoleActivity.class);
                intent.putExtra("person_type", type);
                intent.putExtra("model_type", modelType);
                mContext.startActivity(intent);
            }
        });

        mEditRoleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (App.getApp().chatDataInfo == null) {
                    return;
                }

                Intent intent = new Intent(mContext, EditRoleActivity.class);
                intent.putExtra("role_type",type);
                mContext.startActivity(intent);
                dismiss();
            }
        });
    }
}