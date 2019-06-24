package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.ChatTypeInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.ui.activity.ChatAudioActivity;
import com.yc.wsjt.ui.activity.ChatEmojiActivity;
import com.yc.wsjt.ui.activity.ChatImageActivity;
import com.yc.wsjt.ui.activity.ChatPersonCardActivity;
import com.yc.wsjt.ui.activity.ChatQunSystemInfoActivity;
import com.yc.wsjt.ui.activity.ChatRedPackageActivity;
import com.yc.wsjt.ui.activity.ChatShareActivity;
import com.yc.wsjt.ui.activity.ChatTextActivity;
import com.yc.wsjt.ui.activity.ChatTimeActivity;
import com.yc.wsjt.ui.adapter.ChatTypeAdapter;

import java.util.ArrayList;
import java.util.List;


public class ChatQunTypeDialog extends Dialog {

    private Context mContext;

    RecyclerView chatTypeListView;

    ChatTypeAdapter chatTypeAdapter;


    public ChatQunTypeDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ChatQunTypeDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_chat_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {

        //聊天的类型
        List<ChatTypeInfo> typeList = new ArrayList<>();
        for (int i = 0; i < Constants.qunTypeImages.length; i++) {
            ChatTypeInfo chatTypeInfo = new ChatTypeInfo();
            chatTypeInfo.setTypeImg(Constants.qunTypeImages[i]);
            chatTypeInfo.setTypeName(mContext.getResources().getString(Constants.qunTypeNames[i]));
            typeList.add(chatTypeInfo);
        }

        int tempWidth = (int) (ScreenUtils.getScreenWidth() * 0.92) / 4;
        chatTypeListView = findViewById(R.id.chat_type_list);
        chatTypeAdapter = new ChatTypeAdapter(mContext, typeList, tempWidth);
        chatTypeListView.setLayoutManager(new GridLayoutManager(mContext, 4));
        chatTypeListView.setAdapter(chatTypeAdapter);

        chatTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(mContext, ChatTimeActivity.class);
                        intent.putExtra("is_qunliao", true);
                        mContext.startActivity(intent);
                        dismiss();
                        break;
                    case 1:
                        Intent intent1 = new Intent(mContext, ChatTextActivity.class);
                        intent1.putExtra("is_qunliao", true);
                        mContext.startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(mContext, ChatImageActivity.class);
                        intent2.putExtra("is_qunliao", true);
                        mContext.startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(mContext, ChatAudioActivity.class);
                        intent3.putExtra("is_qunliao", true);
                        mContext.startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(mContext, ChatEmojiActivity.class);
                        intent4.putExtra("is_qunliao", true);
                        mContext.startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(mContext, ChatRedPackageActivity.class);
                        intent5.putExtra("is_qunliao", true);
                        mContext.startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(mContext, ChatRedPackageActivity.class);
                        intent6.putExtra("is_qunliao", true);
                        mContext.startActivity(intent6);
                        break;
                    case 7:
                        Intent intent8 = new Intent(mContext, ChatShareActivity.class);
                        intent8.putExtra("is_qunliao", true);
                        mContext.startActivity(intent8);
                        break;
                    case 8:
                        Intent intent9 = new Intent(mContext, ChatPersonCardActivity.class);
                        intent9.putExtra("is_qunliao", true);
                        mContext.startActivity(intent9);
                        break;
                    case 9:
                        Intent intent11 = new Intent(mContext, ChatQunSystemInfoActivity.class);
                        mContext.startActivity(intent11);
                        break;
                }
                dismiss();
            }
        });
    }
}