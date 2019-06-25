package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.RedPackageMessage;
import com.yc.wsjt.ui.activity.QunHongBaoActivity;


public class QianghongbaoDialog extends Dialog {

    private Context mContext;

    private ImageView mOpenImageView;

    private ImageView mUserHeadIv;

    private TextView mUserNameTv;

    private TextView mRedDescTv;

    private RedPackageMessage redPackageMessage;

    public void setRedPackageMessage(RedPackageMessage redPackageMessage) {
        this.redPackageMessage = redPackageMessage;
        if (this.redPackageMessage != null) {
            Glide.with(mContext).load(redPackageMessage.getMessageUserHead()).into(mUserHeadIv);
            mUserNameTv.setText(redPackageMessage.getMessageUserName());
            mRedDescTv.setText(StringUtils.isEmpty(redPackageMessage.getRedDesc()) ? "恭喜发财，大吉大利" : redPackageMessage.getRedDesc());
        }
    }

    public QianghongbaoDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public QianghongbaoDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qhb_dialog_view);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        Logger.i("red package info--->" + JSON.toJSONString(redPackageMessage));
        mUserHeadIv = findViewById(R.id.iv_user_head);
        mUserNameTv = findViewById(R.id.tv_user_name);
        mRedDescTv = findViewById(R.id.tv_red_desc);

        mOpenImageView = findViewById(R.id.iv_open);
        ImageView closeIv = findViewById(R.id.iv_close);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mOpenImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {

                CustomAnimationDrawableNew cad = new CustomAnimationDrawableNew((AnimationDrawable) ContextCompat.getDrawable(mContext, R.drawable.hongbao_anim)) {
                    @Override
                    public void onAnimationStart() {
                        // Animation has started...
                        Logger.i("anim start --->");
                    }

                    @Override
                    public void onAnimationFinish() {
                        // Animation has finished...
                        Logger.i("anim end --->");
                        Intent intent = new Intent(mContext, QunHongBaoActivity.class);
                        intent.putExtra("redId", redPackageMessage.getId());
                        intent.putExtra("send_user_name", redPackageMessage.getMessageUserName());
                        intent.putExtra("send_user_head", redPackageMessage.getMessageUserHead());
                        intent.putExtra("red_remark", redPackageMessage.getRedDesc());
                        intent.putExtra("red_money", Float.parseFloat(redPackageMessage.getRedNumber()));
                        intent.putExtra("red_count",redPackageMessage.getRedCount());
                        mContext.startActivity(intent);
                        mOpenImageView.setBackground((AnimationDrawable) ContextCompat.getDrawable(mContext, R.drawable.hongbao_anim));
                        dismiss();
                    }
                };
                view.setBackgroundDrawable(cad);

                // Start the animation
                cad.setOneShot(true);
                cad.start();
            }
        });

    }


}