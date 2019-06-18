package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.tabs.TabLayout;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageEvent;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.EmojiFragmentAdapter;
import com.yc.wsjt.ui.custom.Glide4Engine;
import com.yc.wsjt.ui.fragment.DefaultEmojiFragment;
import com.yc.wsjt.ui.fragment.MyEmojiFragment;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/4/29.
 */
public class EmojiListActivity extends BaseActivity {

    public static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.tv_add_emoji)
    TextView mAddEmojiTv;

    @BindView(R.id.tab_layout)
    TabLayout smartTabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_emoji_list;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {

    }

    @Override
    protected void initViews() {
        List<String> mTitleDataList = new ArrayList<>();
        mTitleDataList.add("默认表情");
        mTitleDataList.add("我的表情");

        Fragment[] fragments = new Fragment[]{new DefaultEmojiFragment(), new MyEmojiFragment()};

        EmojiFragmentAdapter viewPageAdapter = new EmojiFragmentAdapter(getSupportFragmentManager(), fragments, mTitleDataList);
        viewPager.setAdapter(viewPageAdapter);

        smartTabLayout.setTabMode(TabLayout.MODE_FIXED);
        smartTabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.tv_add_emoji)
    void addEmoji() {
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .showSingleMediaType(true)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    Logger.i(JSONObject.toJSONString(Matisse.obtainPathResult(data)));
                    if (Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                        //Glide.with(context).load(Uri.fromFile(new File(Matisse.obtainPathResult(data).get(0)))).into(mChooseIv);
                        Logger.i("add image --->" + Matisse.obtainPathResult(data).get(0));
                        MessageEvent event = new MessageEvent();
                        event.setMessageType(1);
                        event.setMessageContent(Matisse.obtainPathResult(data).get(0));
                        EventBus.getDefault().post(event);
                    }
                    break;
            }
        }
    }
}
