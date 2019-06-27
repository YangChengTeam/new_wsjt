package com.yc.wsjt.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.EmojiInfoRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.EmojiInfoPresenterImp;
import com.yc.wsjt.ui.activity.ChatEmojiActivity;
import com.yc.wsjt.ui.adapter.EmojiListAdapter;
import com.yc.wsjt.view.EmojiInfoView;

import butterknife.BindView;

/**
 * Created by admin on 2017/4/20.
 */

public class DefaultEmojiFragment extends BaseFragment implements EmojiInfoView {

    @BindView(R.id.def_emoji_list)
    RecyclerView mEmojiListView;

    EmojiListAdapter emojiListAdapter;

    EmojiInfoPresenterImp emojiInfoPresenterImp;

    @Override
    protected int getContentView() {
        return R.layout.fragment_def_emoji;
    }

    @Override
    public void initVars() {

        emojiListAdapter = new EmojiListAdapter(getActivity(), null);
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
        emojiInfoPresenterImp = new EmojiInfoPresenterImp(this, getActivity());
        emojiInfoPresenterImp.getEmojiList();
    }

    @Override
    protected void initFragmentConfig() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(EmojiInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null && tData.getData().size() > 0) {
                emojiListAdapter.setNewData(tData.getData());
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }
}
