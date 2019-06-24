package com.yc.wsjt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.RoleInfo;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.WeixinqunliaoChatAdapter;
import com.yc.wsjt.ui.custom.ChatQunTypeDialog;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangdinghui on 2019/5/6.
 */
public class WeixinqunliaoActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.tv_setting)
    TextView mSettingTv;

    @BindView(R.id.layout_chat_data_set)
    RelativeLayout mChatDataSetLayout;

    @BindView(R.id.iv_chat_left_head)
    ImageView mLeftHeadIv;

    @BindView(R.id.iv_chat_right_head)
    ImageView mRightHeadTv;

    @BindView(R.id.chat_qun_list_view)
    SwipeRecyclerView mChatListView;

    @BindView(R.id.btn_add_data)
    Button mAddDataBtn;

    @BindView(R.id.btn_pre_show)
    Button mQueryDataBtn;

    WeixinqunliaoChatAdapter weixinqunliaoChatAdapter;

    ChatQunTypeDialog chatQunTypeDialog;

    WindowManager.LayoutParams windowParams;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    Disposable disposable = null;

    public MessageContent messageContent;

    QunChatInfo mQunChatInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weixin_qunliao;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        mTitleTv.setText(getResources().getString(R.string.weixin_qun_liao));
        mSettingTv.setText(getResources().getString(R.string.weixin_setting));
        mSettingTv.setVisibility(View.GONE);
    }

    @Override
    protected void initViews() {
        chatQunTypeDialog = new ChatQunTypeDialog(this, R.style.custom_dialog);

        weixinqunliaoChatAdapter = new WeixinqunliaoChatAdapter(this, null, 0);
        mChatListView.setLayoutManager(new LinearLayoutManager(this));
        mChatListView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.line_color), 1, 1));
        mChatListView.setAdapter(weixinqunliaoChatAdapter);
        mChatListView.setLongPressDragEnabled(true);

        mChatListView.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI

        weixinqunliaoChatAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_delete) {
                    mAppDatabase.weixinQunChatInfoDao().deleteQunWeChatInfo(weixinqunliaoChatAdapter.getData().get(position));
                    weixinqunliaoChatAdapter.getData().remove(position);
                }
            }
        });
    }

    OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            // 此方法在Item拖拽交换位置时被调用。
            // 第一个参数是要交换为之的Item，第二个是目标位置的Item。

            // 交换数据，并更新adapter。
            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();
            Collections.swap(weixinqunliaoChatAdapter.getData(), fromPosition, toPosition);
            weixinqunliaoChatAdapter.notifyItemMoved(fromPosition, toPosition);
            Logger.i("move--->" + fromPosition + "---" + toPosition);
            // 返回true，表示数据交换成功，ItemView可以交换位置。
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            // 此方法在Item在侧滑删除时被调用。

            // 从数据源移除该Item对应的数据，并刷新Adapter。
            int position = srcHolder.getAdapterPosition();
            weixinqunliaoChatAdapter.getData().remove(position);
            weixinqunliaoChatAdapter.notifyItemRemoved(position);
        }
    };

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            //查询是否有主记录
            if (mAppDatabase.messageContentDao().getItemById(PhoneUtils.getDeviceId()) != null) {
                messageContent = mAppDatabase.messageContentDao().getItemById(PhoneUtils.getDeviceId());
            } else {
                //未查询到记录，新增一条
                MessageContent tempMessageContent = new MessageContent();
                tempMessageContent.setDeviceId(PhoneUtils.getDeviceId());
                tempMessageContent.setWxMainId(1);
                mAppDatabase.messageContentDao().insert(tempMessageContent);
                messageContent = mAppDatabase.messageContentDao().getItemById(PhoneUtils.getDeviceId());
            }

            App.getApp().setMessageContent(messageContent);
            Logger.i("main message content--->" + messageContent.wxMainId + "---" + messageContent.deviceId);

            //初始化聊天记录
            disposable = mAppDatabase.weixinQunChatInfoDao()
                    .loadQunWeChatInfo(messageContent.getWxMainId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<WeixinQunChatInfo>>() {
                        @Override
                        public void accept(List<WeixinQunChatInfo> list) {
                            if (list != null) {
                                for (WeixinQunChatInfo qunChatInfo : list) {
                                    Logger.i("insert wechat--->" + qunChatInfo.getChatText());
                                }
                                weixinqunliaoChatAdapter.setNewData(list);
                            }
                        }
                    });

            //资料设置信息
            if (mAppDatabase.qunChatInfoDao().getItemById(PhoneUtils.getDeviceId()) != null) {
                mQunChatInfo = mAppDatabase.qunChatInfoDao().getItemById(PhoneUtils.getDeviceId());
                App.getApp().qunChatInfo = mQunChatInfo;
                List<RoleInfo> roleList = mAppDatabase.roleInfoDao().getListById(mQunChatInfo.getId());
                App.getApp().qunChatInfo.setRoleList(roleList);

                if (roleList != null) {
                    if (roleList.size() > 0) {
                        if (StringUtils.isEmpty(roleList.get(0).getAvatar())) {
                            Glide.with(this).load(roleList.get(0).getAvatarLocalImg()).into(mLeftHeadIv);
                        } else {
                            Glide.with(this).load(roleList.get(0).getAvatar()).into(mLeftHeadIv);
                        }
                    }

                    if (roleList.size() > 1) {
                        if (StringUtils.isEmpty(roleList.get(1).getAvatar())) {
                            Glide.with(this).load(roleList.get(1).getAvatarLocalImg()).into(mRightHeadTv);
                        } else {
                            Glide.with(this).load(roleList.get(1).getAvatar()).into(mRightHeadTv);
                        }
                    }
                }
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_add_data)
    public void addData() {
        if (chatQunTypeDialog != null && !chatQunTypeDialog.isShowing()) {

            chatQunTypeDialog.show();
            chatQunTypeDialog.setCanceledOnTouchOutside(true);

            windowParams = chatQunTypeDialog.getWindow().getAttributes();
            windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.92);
            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            chatQunTypeDialog.getWindow().setAttributes(windowParams);
        }
    }

    @OnClick(R.id.btn_pre_show)
    public void queryData() {
        App.getApp().setQunChatList(weixinqunliaoChatAdapter.getData());
        Intent intent = new Intent(this, ChatSessionActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_chat_data_set)
    void chatDataSet() {
        Intent intent = new Intent(this, QunChatDataSetActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

}
