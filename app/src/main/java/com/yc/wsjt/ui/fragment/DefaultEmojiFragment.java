package com.yc.wsjt.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.wsjt.R;
import com.yc.wsjt.ui.activity.ChatEmojiActivity;
import com.yc.wsjt.ui.adapter.EmojiListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by admin on 2017/4/20.
 */

public class DefaultEmojiFragment extends BaseFragment {

    @BindView(R.id.def_emoji_list)
    RecyclerView mEmojiListView;

    EmojiListAdapter emojiListAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_def_emoji;
    }

    @Override
    public void initVars() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            list.add(i + "A");
        }
        emojiListAdapter = new EmojiListAdapter(getActivity(), list);
        mEmojiListView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
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

    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {

    }

    @Override
    protected void initFragmentConfig() {

    }

}
