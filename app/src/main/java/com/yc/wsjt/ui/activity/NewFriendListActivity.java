package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.FriendInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.NewFriendListAdapter;
import com.yc.wsjt.ui.custom.OpenVipDialog;
import com.yc.wsjt.ui.custom.VipPayTypeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/27.
 */
public class NewFriendListActivity extends BaseActivity implements VipPayTypeDialog.PayListener {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.tv_setting)
    TextView mAddFriendTv;

    @BindView(R.id.new_friend_list)
    RecyclerView mNewFriendListView;

    NewFriendListAdapter newFriendListAdapter;

    public String[] userNames = {"周小姐", "王小明", "张大大", "徐晓清"};

    public int[] heads = {R.mipmap.c1, R.mipmap.c2, R.mipmap.c3, R.mipmap.c4};

    private boolean isUse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_friend_list;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initViews() {
        mTitleTv.setText("新的朋友");
        mAddFriendTv.setText("添加朋友");
        mAddFriendTv.setTextColor(ContextCompat.getColor(this, R.color.black1));

        List<FriendInfo> list = new ArrayList<>();
        for (int i = 0; i < userNames.length; i++) {
            FriendInfo friendInfo = new FriendInfo();
            friendInfo.setNickName(userNames[i]);
            friendInfo.setRemark("我是" + userNames[i]);
            friendInfo.setLocalHead(heads[i]);
            list.add(friendInfo);
        }

        newFriendListAdapter = new NewFriendListAdapter(this, list);
        mNewFriendListView.setLayoutManager(new LinearLayoutManager(this));
        mNewFriendListView.setAdapter(newFriendListAdapter);

        newFriendListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.layout_accept) {
                    boolean isReceive = !newFriendListAdapter.getData().get(position).isReceive();
                    newFriendListAdapter.getData().get(position).setReceive(isReceive);
                    newFriendListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isUse = bundle.getBoolean("is_use", false);
        }

        openVipDialog = new OpenVipDialog(this, R.style.custom_dialog);
        openVipDialog.setVipListener(this);
        vipPayTypeDialog = new VipPayTypeDialog(this, R.style.custom_dialog);
        vipPayTypeDialog.setPayListener(this);

        if (!isUse) {
            if (openVipDialog != null && !openVipDialog.isShowing()) {
                openVipDialog.show();
                return;
            }
        }
    }

    @Override
    public void addComment() {
    }

    @Override
    public void openVip() {
        if (vipPayTypeDialog != null && !vipPayTypeDialog.isShowing()) {
            vipPayTypeDialog.show();
        }
    }

    @Override
    public void closeOpenVip() {
        finish();
    }

    @Override
    public void pay() {
        Logger.i("打开支付--->");
    }

    @Override
    public void payClose() {
        Logger.i("支付界面关闭--->");
        finish();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
