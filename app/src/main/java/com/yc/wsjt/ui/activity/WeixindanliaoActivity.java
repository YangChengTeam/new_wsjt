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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.ChatDataInfo;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.WeixindanliaoChatAdapter;
import com.yc.wsjt.ui.custom.ChatTypeDialog;

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
public class WeixindanliaoActivity extends BaseActivity {

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

    @BindView(R.id.chat_list_view)
    SwipeRecyclerView mChatListView;

    @BindView(R.id.btn_add_data)
    Button mAddDataBtn;

    @BindView(R.id.btn_pre_show)
    Button mQueryDataBtn;

    WeixindanliaoChatAdapter weixindanliaoChatAdapter;

    ChatTypeDialog chatTypeDialog;

    WindowManager.LayoutParams windowParams;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    Disposable disposable = null;

    public MessageContent messageContent;

    ChatDataInfo mChatDataInfo;

    private int modelType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weixin_danliao;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
        mTitleTv.setText(getResources().getString(R.string.weixin_danliao));
        mSettingTv.setText(getResources().getString(R.string.weixin_setting));
        mSettingTv.setVisibility(View.GONE);
    }

    @Override
    protected void initViews() {

        chatTypeDialog = new ChatTypeDialog(this, R.style.custom_dialog);

        weixindanliaoChatAdapter = new WeixindanliaoChatAdapter(this, null, 0);
        mChatListView.setLayoutManager(new LinearLayoutManager(this));
        mChatListView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.line_color), 1, 1));
        mChatListView.setAdapter(weixindanliaoChatAdapter);
        mChatListView.setLongPressDragEnabled(true);

        mChatListView.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI

        weixindanliaoChatAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_delete) {
                    mAppDatabase.weixinChatInfoDao().deleteWeChatInfo(weixindanliaoChatAdapter.getData().get(position));
                    weixindanliaoChatAdapter.getData().remove(position);
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
            Collections.swap(weixindanliaoChatAdapter.getData(), fromPosition, toPosition);
            weixindanliaoChatAdapter.notifyItemMoved(fromPosition, toPosition);
            Logger.i("move--->" + fromPosition + "---" + toPosition);
            // 返回true，表示数据交换成功，ItemView可以交换位置。
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            // 此方法在Item在侧滑删除时被调用。

            // 从数据源移除该Item对应的数据，并刷新Adapter。
            int position = srcHolder.getAdapterPosition();
            weixindanliaoChatAdapter.getData().remove(position);
            weixindanliaoChatAdapter.notifyItemRemoved(position);
        }
    };

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            modelType = bundle.getInt("model_type", 0);
        }

        try {
            //初始化设置参数

            if (mAppDatabase.chatDataInfoDao().getItemById(PhoneUtils.getDeviceId(), modelType) != null) {
                mChatDataInfo = mAppDatabase.chatDataInfoDao().getItemById(PhoneUtils.getDeviceId(), modelType);
                RequestOptions options = new RequestOptions();
                options.placeholder(R.mipmap.user_head_def);
                options.error(R.mipmap.user_head_def);
                Glide.with(getApplicationContext()).load(mChatDataInfo.getPersonHead()).apply(options).into(mLeftHeadIv);
                Glide.with(getApplicationContext()).load(mChatDataInfo.getOtherPersonHead()).apply(options).into(mRightHeadTv);
                App.getApp().chatDataInfo = mChatDataInfo;
            } else {
                mChatDataInfo = new ChatDataInfo();
                mChatDataInfo.setDeviceId(PhoneUtils.getDeviceId());
                if (App.getApp().isLogin && App.getApp().mUserInfo != null) {
                    mChatDataInfo.setPersonHead(App.getApp().mUserInfo.getFace());
                    mChatDataInfo.setPersonName(App.getApp().mUserInfo.getNickName());
                } else {
                    mChatDataInfo.setPersonHeadLocal(R.mipmap.user_head_def);
                    mChatDataInfo.setPersonName("未知发送人");
                }
                mChatDataInfo.setOtherPersonHeadLocal(R.mipmap.user_head_def);
                mChatDataInfo.setOtherPersonName("未知用户");
                mChatDataInfo.setModelType(modelType);
                mAppDatabase.chatDataInfoDao().insert(mChatDataInfo);
                App.getApp().chatDataInfo = mChatDataInfo;

                RequestOptions options = new RequestOptions();
                options.placeholder(R.mipmap.user_head_def);
                options.error(R.mipmap.user_head_def);
                Glide.with(getApplicationContext()).load(mChatDataInfo.getPersonHead()).apply(options).into(mLeftHeadIv);
                Glide.with(getApplicationContext()).load(mChatDataInfo.getOtherPersonHead()).apply(options).into(mRightHeadTv);
            }

            //查询是否有主记录
            if (mAppDatabase.messageContentDao().getItemById(PhoneUtils.getDeviceId(), modelType) != null) {
                messageContent = mAppDatabase.messageContentDao().getItemById(PhoneUtils.getDeviceId(), modelType);
            } else {
                //未查询到记录，新增一条
                MessageContent tempMessageContent = new MessageContent();
                tempMessageContent.setDeviceId(PhoneUtils.getDeviceId());
                tempMessageContent.setModelType(modelType);
                Long mainId = mAppDatabase.messageContentDao().insert(tempMessageContent);
                messageContent = mAppDatabase.messageContentDao().getItemById(PhoneUtils.getDeviceId(), modelType);
            }

            App.getApp().setMessageContent(messageContent);
            Logger.i("main message content--->" + messageContent.getId() + "---" + messageContent.deviceId);

            //初始化聊天记录
            disposable = mAppDatabase.weixinChatInfoDao()
                    .loadWeChatInfo(messageContent.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<WeixinChatInfo>>() {
                        @Override
                        public void accept(List<WeixinChatInfo> entities) {
                            if (entities != null) {
                                for (WeixinChatInfo weixinChatInfo : entities) {
                                    Logger.i("insert wechat--->" + weixinChatInfo.getChatText());
                                }
                                weixindanliaoChatAdapter.setNewData(entities);
                            }
                        }
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_add_data)
    public void addData() {
        if (chatTypeDialog != null && !chatTypeDialog.isShowing()) {

            chatTypeDialog.show();
            chatTypeDialog.setCanceledOnTouchOutside(true);

            windowParams = chatTypeDialog.getWindow().getAttributes();
            windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.92);
            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            chatTypeDialog.getWindow().setAttributes(windowParams);
        }
    }

    @OnClick(R.id.btn_pre_show)
    public void queryData() {
        App.getApp().setChatList(weixindanliaoChatAdapter.getData());
        Intent intent = new Intent(this, ChatSessionActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_chat_data_set)
    void chatDataSet() {
        Intent intent = new Intent(this, ChatDataSetActivity.class);
        intent.putExtra("model_type", modelType);
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
