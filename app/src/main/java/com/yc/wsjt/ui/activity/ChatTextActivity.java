package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.TextMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.ui.adapter.EmotionGridViewAdapter;
import com.yc.wsjt.ui.adapter.EmotionPagerAdapter;
import com.yc.wsjt.ui.custom.EmojiIndicatorView;
import com.yc.wsjt.ui.custom.GlobalOnItemClickManagerUtils;
import com.yc.wsjt.ui.custom.RoleSelectDialog;
import com.yc.wsjt.util.EmotionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatTextActivity extends BaseActivity implements RoleSelectDialog.ChooseRoleListener {

    private EmotionPagerAdapter emotionPagerGvAdapter;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.layout_send_info)
    RelativeLayout mSendInfoLayout;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.iv_send_user_head)
    ImageView mSendUserHeadIv;

    @BindView(R.id.et_chat_input)
    EditText mChatEditText;

    @BindView(R.id.vp_complate_emotion_layout)
    ViewPager vp_complate_emotion_layout;

    @BindView(R.id.ll_point_group)
    EmojiIndicatorView ll_point_group;//表情面板对应的点列表

    @BindView(R.id.btn_def_face)
    Button mDefBtn;

    @BindView(R.id.btn_emoji_face)
    Button mEmojiBtn;

    boolean isMySelf = true;

    private int emotion_map_type = EmotionUtils.EMOTION_CLASSIC_TYPE;

    private boolean isQunLiao;

    private int CHAT_TYPE = MessageContent.SEND_TEXT;

    private String sendUserName;

    private String sendUserHead;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_text;
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
        initEmotion(emotion_map_type);
        initListener();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isQunLiao = bundle.getBoolean("is_qunliao", false);
        }
        mTitleTv.setText(isQunLiao ? "群聊文本" : "单聊文本");

        if (!isQunLiao && App.getApp().chatDataInfo != null) {
            boolean isMySelf = SPUtils.getInstance().getBoolean(Constants.IS_SELF, true);
            mSendUserNameTv.setText(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());

            if (isMySelf) {
                if (StringUtils.isEmpty(App.getApp().chatDataInfo.getPersonHead())) {
                    Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                } else {
                    Glide.with(this).load(App.getApp().chatDataInfo.getPersonHead()).into(mSendUserHeadIv);
                }
            } else {
                if (StringUtils.isEmpty(App.getApp().chatDataInfo.getOtherPersonHead())) {
                    Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                } else {
                    Glide.with(this).load(App.getApp().chatDataInfo.getOtherPersonHead()).into(mSendUserHeadIv);
                }
            }
        } else {
            if (App.getApp().qunChatInfo != null) {
                sendUserName = App.getApp().mUserInfo.getNickName();
                sendUserHead = App.getApp().mUserInfo.getFace();
                mSendUserNameTv.setText(sendUserName);
                Glide.with(this).load(sendUserHead).into(mSendUserHeadIv);
            }
        }
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

        vp_complate_emotion_layout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ll_point_group.playByStartPointToNext(oldPagerPos, position);
                oldPagerPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化表情面板
     * 思路：获取表情的总数，按每行存放7个表情，动态计算出每个表情所占的宽度大小（包含间距），
     * 而每个表情的高与宽应该是相等的，这里我们约定只存放3行
     * 每个面板最多存放7*3=21个表情，再减去一个删除键，即每个面板包含20个表情
     * 根据表情总数，循环创建多个容量为20的List，存放表情，对于大小不满20进行特殊
     * 处理即可。
     */
    private void initEmotion(int type) {
        // 获取屏幕宽度
        int screenWidth = ScreenUtils.getScreenWidth();
        // item的间距
        int spacing = SizeUtils.dp2px(12);
        // 动态计算item的宽度和高度
        int itemWidth = (screenWidth - spacing * 8) / 7;
        //动态计算gridview的总高度
        int gvHeight = itemWidth * 3 + spacing * 6;

        List<GridView> emotionViews = new ArrayList<>();
        List<String> emotionNames = new ArrayList<>();
        // 遍历所有的表情的key
        for (String emojiName : EmotionUtils.getEmojiMap(type).keySet()) {
            emotionNames.add(emojiName);
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size() == 20) {
                GridView gv = createEmotionGridView(type, emotionNames, screenWidth, spacing, itemWidth, gvHeight);
                emotionViews.add(gv);
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = new ArrayList<>();
            }
        }

        // 判断最后是否有不足20个表情的剩余情况
        if (emotionNames.size() > 0) {
            GridView gv = createEmotionGridView(type, emotionNames, screenWidth, spacing, itemWidth, gvHeight);
            emotionViews.add(gv);
        }

        //初始化指示器
        ll_point_group.initIndicator(emotionViews.size());
        // 将多个GridView添加显示到ViewPager中
        emotionPagerGvAdapter = new EmotionPagerAdapter(emotionViews);
        vp_complate_emotion_layout.setAdapter(emotionPagerGvAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        vp_complate_emotion_layout.setLayoutParams(params);
    }

    /**
     * 创建显示表情的GridView
     */
    private GridView createEmotionGridView(int type, List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        // 创建GridView
        GridView gv = new GridView(this);
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent);
        //设置7列
        gv.setNumColumns(7);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding * 2);
        //设置GridView的宽高
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);
        // 给GridView设置表情图片
        EmotionGridViewAdapter adapter = new EmotionGridViewAdapter(this, emotionNames, itemWidth, type);
        gv.setAdapter(adapter);
        //设置全局点击事件
        //gv.setOnItemClickListener(GlobalOnItemClickManagerUtils.getInstance(getActivity()).getOnItemClickListener(emotion_map_type));

        GlobalOnItemClickManagerUtils globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance(this);
        //绑定当前Bar的编辑框
        globalOnItemClickManager.attachToEditText(mChatEditText);
        gv.setOnItemClickListener(globalOnItemClickManager.getOnItemClickListener(type));
        return gv;
    }

    @OnClick(R.id.btn_def_face)
    void defFace() {
        initEmotion(EmotionUtils.EMOTION_CLASSIC_TYPE);
        mDefBtn.setBackgroundResource(R.drawable.choose_type_selected);
        mDefBtn.setTextColor(ContextCompat.getColor(this, R.color.white));
        mEmojiBtn.setBackgroundResource(R.drawable.choose_type_normal);
        mEmojiBtn.setTextColor(ContextCompat.getColor(this, R.color.color_blue));
    }

    @OnClick(R.id.btn_emoji_face)
    void emojiFace() {
        initEmotion(EmotionUtils.EMOJI_TYPE);
        mDefBtn.setBackgroundResource(R.drawable.choose_type_normal);
        mDefBtn.setTextColor(ContextCompat.getColor(this, R.color.color_blue));
        mEmojiBtn.setBackgroundResource(R.drawable.choose_type_selected);
        mEmojiBtn.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    @OnClick(R.id.btn_config)
    void config() {

        if (isQunLiao) {
            if (StringUtils.isEmpty(sendUserName)) {
                ToastUtils.showLong("请选择发送人");
                return;
            }

            if (StringUtils.isEmpty(mChatEditText.getText())) {
                ToastUtils.showLong("请输入消息内容");
                return;
            }

            //插入一条时间设置记录
            TextMessage textMessage = new TextMessage();
            //textMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            textMessage.setMessageType(CHAT_TYPE);
            textMessage.setMessageUserName(sendUserName);
            textMessage.setMessageUserHead(sendUserHead);
            textMessage.setMessageContent(mChatEditText.getText().toString());
            Long txtId = mAppDatabase.textMessageDao().insert(textMessage);

            WeixinQunChatInfo weixinQunChatInfo = new WeixinQunChatInfo();
            weixinQunChatInfo.setMainId(App.getApp().getMessageContent().getId());
            weixinQunChatInfo.setTypeIcon(R.mipmap.type_text);
            weixinQunChatInfo.setChildTabId(txtId);
            weixinQunChatInfo.setType(CHAT_TYPE);
            mAppDatabase.weixinQunChatInfoDao().insert(weixinQunChatInfo);
        } else {
            if (StringUtils.isEmpty(mChatEditText.getText())) {
                ToastUtils.showLong("请输入消息内容");
                return;
            }

            CHAT_TYPE = MessageContent.SEND_TEXT;
            if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
                CHAT_TYPE = MessageContent.RECEIVE_TEXT;
            }

            //插入一条时间设置记录
            TextMessage textMessage = new TextMessage();
            //textMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            textMessage.setMessageType(CHAT_TYPE);
            textMessage.setMessageUserName(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
            textMessage.setMessageUserHead(isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead());
            textMessage.setLocalMessageImg(R.mipmap.type_text);
            textMessage.setMessageContent(mChatEditText.getText().toString());
            Long txtId = mAppDatabase.textMessageDao().insert(textMessage);

            WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
            weixinChatInfo.setMainId(App.getApp().getMessageContent().getId());
            weixinChatInfo.setTypeIcon(R.mipmap.type_text);
            weixinChatInfo.setChildTabId(txtId);
            weixinChatInfo.setType(CHAT_TYPE);
            mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);
        }
        finish();
    }

    @OnClick(R.id.layout_send_info)
    void changeSendRole() {
        if (isQunLiao) {
            RoleSelectDialog roleSelectDialog = new RoleSelectDialog(this, R.style.custom_dialog);
            roleSelectDialog.setChooseRoleListener(this);
            roleSelectDialog.show();

            roleSelectDialog.setCanceledOnTouchOutside(true);
            WindowManager.LayoutParams windowParams = roleSelectDialog.getWindow().getAttributes();
            windowParams.width = (int) (ScreenUtils.getScreenWidth() * 0.92);
            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            roleSelectDialog.getWindow().setAttributes(windowParams);
        } else {
            if (App.getApp().chatDataInfo != null) {
                isMySelf = SPUtils.getInstance().getBoolean(Constants.IS_SELF, true);
                isMySelf = !isMySelf;
                SPUtils.getInstance().put(Constants.IS_SELF, isMySelf);
                mSendUserNameTv.setText(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
                if (isMySelf) {
                    if (StringUtils.isEmpty(App.getApp().chatDataInfo.getPersonHead())) {
                        Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                    } else {
                        Glide.with(this).load(App.getApp().chatDataInfo.getPersonHead()).into(mSendUserHeadIv);
                    }
                } else {
                    if (StringUtils.isEmpty(App.getApp().chatDataInfo.getOtherPersonHead())) {
                        Glide.with(this).load(R.mipmap.user_head_def).into(mSendUserHeadIv);
                    } else {
                        Glide.with(this).load(App.getApp().chatDataInfo.getOtherPersonHead()).into(mSendUserHeadIv);
                    }
                }
            }
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void chooseRole(int pos, String name, String head, int localHead) {
        if (pos > 0) {
            CHAT_TYPE = MessageContent.RECEIVE_TEXT;
        } else {
            CHAT_TYPE = MessageContent.SEND_TEXT;
        }
        mSendUserNameTv.setText(name);
        sendUserName = name;
        Glide.with(this).load(head).into(mSendUserHeadIv);
        sendUserHead = head;
    }
}
