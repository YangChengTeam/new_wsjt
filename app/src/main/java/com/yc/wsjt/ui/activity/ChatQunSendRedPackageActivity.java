package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.RedPackageMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.bean.WeixinQunChatInfo;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatQunSendRedPackageActivity extends BaseActivity {

    private static final int REQUEST_CODE_CHOOSE = 1000;

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.btn_config)
    Button mConfigBtn;

    @BindView(R.id.layout_send_info)
    RelativeLayout mSendInfoLayout;

    @BindView(R.id.tv_send_user_name)
    TextView mSendUserNameTv;

    @BindView(R.id.layout_red_number)
    LinearLayout mRedNumberLayout;

    @BindView(R.id.et_red_count)
    EditText mRedCountEt;

    @BindView(R.id.et_red_number)
    EditText mRedNumberEt;

    @BindView(R.id.et_red_remark)
    EditText mRedRemarkEt;

    private File outputImage;

    private String otherSideImgUrl;

    private String replyImgUrl;

    private int chooseType = 1;//发，收

    boolean isMySelf = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qun_send_red_package;
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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_config)
    void config() {
        if (chooseType == 1) {
            if (StringUtils.isEmpty(mRedNumberEt.getText())) {
                ToastUtils.showLong("请输入金额");
                return;
            }
        }

        int type = MessageContent.SEND_RED_PACKET;
        if (!SPUtils.getInstance().getBoolean(Constants.IS_SELF, true)) {
            type = MessageContent.RECEIVE_RED_PACKET;
        }

        //插入一条时间设置记录
        RedPackageMessage redPackageMessage = new RedPackageMessage();
        redPackageMessage.setWxMainId(App.getApp().getMessageContent().getWxMainId());
        redPackageMessage.setRedNumber(mRedNumberEt.getText().toString());
        redPackageMessage.setRedDesc(StringUtils.isEmpty(mRedRemarkEt.getText()) ? mRedRemarkEt.getText().toString() : "恭喜发财，大吉大利!");
        redPackageMessage.setMessageType(type);
        redPackageMessage.setRedType(chooseType);
        redPackageMessage.setMessageUserName(isMySelf ? App.getApp().chatDataInfo.getPersonName() : App.getApp().chatDataInfo.getOtherPersonName());
        redPackageMessage.setMessageUserHead(isMySelf ? App.getApp().chatDataInfo.getPersonHead() : App.getApp().chatDataInfo.getOtherPersonHead());
        redPackageMessage.setOtherSideEmojiUrl(otherSideImgUrl);
        redPackageMessage.setReplyEmojiUrl(replyImgUrl);
        redPackageMessage.setLocalMessageImg(R.mipmap.type_hongbao);
        Long redId = mAppDatabase.redMessageDao().insert(redPackageMessage);
        if (1 == 1) {
            //插入到外层的列表中
            WeixinQunChatInfo weixinQunChatInfo = new WeixinQunChatInfo();
            weixinQunChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            weixinQunChatInfo.setTypeIcon(R.mipmap.type_hongbao);
            weixinQunChatInfo.setType(type);
            weixinQunChatInfo.setChildTabId(redId);
            mAppDatabase.weixinQunChatInfoDao().insert(weixinQunChatInfo);
        } else {
            //插入到外层的列表中
            WeixinChatInfo weixinChatInfo = new WeixinChatInfo();
            weixinChatInfo.setWxMainId(App.getApp().getMessageContent().getWxMainId());
            weixinChatInfo.setTypeIcon(R.mipmap.type_hongbao);
            weixinChatInfo.setType(type);
            weixinChatInfo.setChildTabId(redId);
            mAppDatabase.weixinChatInfoDao().insert(weixinChatInfo);
        }
        finish();
    }
}
