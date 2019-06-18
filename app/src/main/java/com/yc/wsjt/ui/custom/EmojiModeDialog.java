package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.yc.wsjt.R;


public class EmojiModeDialog extends Dialog {

    private static final int REQUEST_CODE = 1000;

    private Context mContext;

    TextView mCustomEmojiTv;

    TextView mNoneEmojiTv;

    TextView mDialogTitleTv;

    private int type;

    private String title;

    public interface ModeClickListener {
        void customEmoji();

        void noneEmoji();
    }

    public ModeClickListener modeClickListener;

    public void setModeClickListener(ModeClickListener modeClickListener) {
        this.modeClickListener = modeClickListener;
    }

    public void setType(int type) {
        this.type = type;
    }

    public EmojiModeDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public EmojiModeDialog(Context context, int themeResId, String title) {
        super(context, themeResId);
        this.mContext = context;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emoji_mode_dialog_view);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        mCustomEmojiTv = findViewById(R.id.tv_custom_emoji);
        mNoneEmojiTv = findViewById(R.id.tv_none_emoji);
        mDialogTitleTv = findViewById(R.id.tv_dialog_title);
        mDialogTitleTv.setText(StringUtils.isEmpty(title) ? "请选择" : title);
        mCustomEmojiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                modeClickListener.customEmoji();
            }
        });

        mNoneEmojiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modeClickListener.noneEmoji();
                dismiss();
            }
        });
    }
}