package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yc.wsjt.R;


public class ChatBgDialog extends Dialog {

    private Context mContext;

    TextView mChatCustomTv;

    TextView mChatNoBgTv;

    public interface ChatBgListener {
        void customBg();

        void chatNoBg();
    }

    public ChatBgListener chatBgListener;

    public void setChatBgListener(ChatBgListener chatBgListener) {
        this.chatBgListener = chatBgListener;
    }

    public ChatBgDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ChatBgDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_bg_dialog_view);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        mChatCustomTv = findViewById(R.id.tv_chat_custom);
        mChatNoBgTv = findViewById(R.id.tv_chat_no_bg);
        mChatCustomTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                chatBgListener.customBg();
            }
        });

        mChatNoBgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                chatBgListener.chatNoBg();
            }
        });
    }
}