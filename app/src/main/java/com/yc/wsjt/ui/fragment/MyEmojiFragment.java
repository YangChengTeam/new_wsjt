package com.yc.wsjt.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageEvent;
import com.yc.wsjt.ui.activity.ChatEmojiActivity;
import com.yc.wsjt.ui.adapter.EmojiListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by admin on 2017/4/20.
 */

public class MyEmojiFragment extends BaseFragment {

    @BindView(R.id.my_emoji_list)
    RecyclerView mEmojiListView;

    EmojiListAdapter emojiListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_emoji;
    }

    @Override
    public void initVars() {
        mEmojiListView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        emojiListAdapter = new EmojiListAdapter(getActivity(), null);
        mEmojiListView.setAdapter(emojiListAdapter);

        emojiListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("emoji_url", R.mipmap.share_pre);
                //设置返回数据
                getActivity().setResult(ChatEmojiActivity.REQUEST_CODE_EMOJI, intent);
                //关闭Activity
                getActivity().finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Logger.i("onMessageEvent --->" + event.getMessageContent() + "---" + event.getMessageType());
        //emojiListAdapter.addData(0, event.getMessageContent());
    }

    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {

    }

    @Override
    protected void initFragmentConfig() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
