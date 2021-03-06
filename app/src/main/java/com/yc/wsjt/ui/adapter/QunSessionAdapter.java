package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.AudioMessage;
import com.yc.wsjt.bean.EmojiMessage;
import com.yc.wsjt.bean.GroupMessage;
import com.yc.wsjt.bean.ImageMessage;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.PersonMessage;
import com.yc.wsjt.bean.RedPackageMessage;
import com.yc.wsjt.bean.ShareMessage;
import com.yc.wsjt.bean.TransferMessage;
import com.yc.wsjt.ui.custom.GlideRoundTransform;

import java.util.List;

/**
 * Created by zhangdinghui on 2019/5/15.
 */
public class QunSessionAdapter extends LQRAdapterForRecyclerView<MessageContent> {

    private Context mContext;

    private List<MessageContent> mData;

    public static final int CHAT_DATE = R.layout.chat_item_date;

    private static final int SEND_TEXT = R.layout.item_text_send;

    private static final int RECEIVE_TEXT = R.layout.item_text_receive;

    private static final int SEND_IMAGE = R.layout.item_image_send;

    private static final int RECEIVE_IMAGE = R.layout.item_image_receive;

    private static final int SEND_IMAGE_FOR_VIDEO = R.layout.item_image_for_video_send;

    private static final int RECEIVE_IMAGE_FOR_VIDEO = R.layout.item_image_for_video_receive;

    public static final int SEND_VOICE = R.layout.item_audio_send;

    public static final int RECEIVE_VOICE = R.layout.item_audio_receive;

    private static final int SEND_EMOJI = R.layout.item_emoji_send;

    private static final int RECEIVE_EMOJI = R.layout.item_emoji_receive;

    private static final int SEND_RED_PACKET = R.layout.item_red_packet_send;

    private static final int RECEIVE_RED_PACKET = R.layout.item_red_packet_receive;

    private static final int SEND_TRANSFER = R.layout.item_transfer_send;

    private static final int RECEIVE_TRANSFER = R.layout.item_transfer_receive;

    private static final int SEND_VIDEO = R.layout.item_video_send;

    private static final int RECEIVE_VIDEO = R.layout.item_video_receive;

    private static final int SEND_SHARE = R.layout.item_share_send;

    private static final int RECEIVE_SHARE = R.layout.item_share_receive;

    private static final int SEND_PERSON_CARD = R.layout.item_person_card_send;

    private static final int RECEIVE_PERSON_CARD = R.layout.item_person_card_receive;

    private static final int SEND_JOIN_GROUP = R.layout.item_group_send;

    private static final int RECEIVE_JOIN_GROUP = R.layout.item_group_receive;

    private static final int SYSTEM_TIPS = R.layout.chat_item_system_tips;

    private RequestOptions options;

    public QunSessionAdapter(Context context, List<MessageContent> data) {
        super(context, data);
        mContext = context;
        mData = data;
        options = new RequestOptions();
        options.placeholder(R.mipmap.user_head_def).transform(new GlideRoundTransform(mContext, 4));
    }

    @Override
    public void convert(LQRViewHolderForRecyclerView helper, MessageContent messageContent, int position) {

        if (messageContent.getMessageType() == MessageContent.CHAT_DATE) {
            helper.setText(R.id.tv_chat_time, messageContent.getMessageContent());
        }

        if (messageContent.getMessageType() == MessageContent.SEND_TEXT || messageContent.getMessageType() == MessageContent.RECEIVE_TEXT) {
            helper.setText(R.id.tv_chat_text, messageContent.getMessageContent());
            Glide.with(mContext).load(messageContent.getMessageUserHead()).apply(options).into((ImageView) helper.getView(R.id.iv_chat_head));
        }

        if (messageContent.getMessageType() == MessageContent.SEND_IMAGE || messageContent.getMessageType() == MessageContent.RECEIVE_IMAGE) {
            Glide.with(mContext).load(((ImageMessage) messageContent).getImageUrl()).into((ImageView) helper.getView(R.id.iv_big_image));
            Glide.with(mContext).load(messageContent.getMessageUserHead()).apply(options).into((ImageView) helper.getView(R.id.iv_chat_head));
        }

        if (messageContent.getMessageType() == MessageContent.SEND_IMAGE_FOR_VIDEO || messageContent.getMessageType() == MessageContent.RECEIVE_IMAGE_FOR_VIDEO) {
            Glide.with(mContext).load(((ImageMessage) messageContent).getImageUrl()).into((ImageView) helper.getView(R.id.iv_video_bg));
            Glide.with(mContext).load(messageContent.getMessageUserHead()).apply(options).into((ImageView) helper.getView(R.id.iv_chat_head));
        }

        if (messageContent.getMessageType() == MessageContent.SEND_VOICE || messageContent.getMessageType() == MessageContent.RECEIVE_VOICE) {
            helper.setText(R.id.tv_audio_size, ((AudioMessage) messageContent).getAudioTime() + "''");
            Glide.with(mContext).load(messageContent.getMessageUserHead()).apply(options).into((ImageView) helper.getView(R.id.iv_chat_head));
        }

        if (messageContent.getMessageType() == MessageContent.SEND_EMOJI || messageContent.getMessageType() == MessageContent.RECEIVE_EMOJI) {
            Glide.with(mContext).load(((EmojiMessage) messageContent).getEmojiUrl()).into((ImageView) helper.getView(R.id.iv_emoji));
            Glide.with(mContext).load(messageContent.getMessageUserHead()).apply(options).into((ImageView) helper.getView(R.id.iv_chat_head));
        }

        if (messageContent.getMessageType() == MessageContent.SEND_RED_PACKET || messageContent.getMessageType() == MessageContent.RECEIVE_RED_PACKET) {
            helper.setText(R.id.tv_red_remark, ((RedPackageMessage) messageContent).getRedDesc());
            Glide.with(mContext).load(messageContent.getMessageUserHead()).apply(options).into((ImageView) helper.getView(R.id.iv_chat_head));
        }
        if (messageContent.getMessageType() == MessageContent.SEND_TRANSFER || messageContent.getMessageType() == MessageContent.RECEIVE_TRANSFER) {
            helper.setText(R.id.tv_trans_number, "¥" + ((TransferMessage) messageContent).getTransferNum());
            helper.setText(R.id.tv_trans_remark, ((TransferMessage) messageContent).getTransferDesc());
            Glide.with(mContext).load(messageContent.getMessageUserHead()).apply(options).into((ImageView) helper.getView(R.id.iv_chat_head));
        }

        if (messageContent.getMessageType() == MessageContent.SEND_VIDEO || messageContent.getMessageType() == MessageContent.RECEIVE_VIDEO) {
            helper.setText(R.id.tv_video_message, messageContent.getMessageContent());
            Glide.with(mContext).load(messageContent.getMessageUserHead()).apply(options).into((ImageView) helper.getView(R.id.iv_chat_head));
        }

        if (messageContent.getMessageType() == MessageContent.SEND_SHARE || messageContent.getMessageType() == MessageContent.RECEIVE_SHARE) {
            helper.setText(R.id.tv_share_title, ((ShareMessage) messageContent).getShareTitle());
            helper.setText(R.id.tv_share_content, ((ShareMessage) messageContent).getShareContent());
            Glide.with(mContext).load(((ShareMessage) messageContent).getShareThumb()).into((ImageView) helper.getView(R.id.iv_share_thumb));
            Glide.with(mContext).load(messageContent.getMessageUserHead()).apply(options).into((ImageView) helper.getView(R.id.iv_chat_head));
        }

        if (messageContent.getMessageType() == MessageContent.SEND_PERSON_CARD || messageContent.getMessageType() == MessageContent.RECEIVE_PERSON_CARD) {
            Glide.with(mContext).load(((PersonMessage) messageContent).getPersonHeadImg()).into((ImageView) helper.getView(R.id.iv_card_head));
            helper.setText(R.id.tv_card_name, ((PersonMessage) messageContent).getPersonName());
            helper.setText(R.id.tv_weixin_number, ((PersonMessage) messageContent).getWeixinNumber() + "");
            Glide.with(mContext).load(messageContent.getMessageUserHead()).apply(options).into((ImageView) helper.getView(R.id.iv_chat_head));
        }
        if (messageContent.getMessageType() == MessageContent.SEND_JOIN_GROUP || messageContent.getMessageType() == MessageContent.RECEIVE_JOIN_GROUP) {
            helper.setText(R.id.tv_group_title, ((GroupMessage) messageContent).getGroupName());
            helper.setText(R.id.tv_group_content, messageContent.getMessageContent());
            if(!StringUtils.isEmpty(((GroupMessage) messageContent).getGroupHead())){
                Glide.with(mContext).load(((GroupMessage) messageContent).getGroupHead()).into((ImageView) helper.getView(R.id.iv_group_thumb));
            }
            Glide.with(mContext).load(messageContent.getMessageUserHead()).apply(options).into((ImageView) helper.getView(R.id.iv_chat_head));
        }
        if (messageContent.getMessageType() == MessageContent.SYSTEM_TIPS) {
            helper.setText(R.id.tv_system_info, messageContent.getMessageContent());
        }
    }

    @Override
    public int getItemViewType(int position) {
        MessageContent msg = mData.get(position);
        int layoutView = 0;
        switch (msg.getMessageType()) {
            case 0:
                layoutView = CHAT_DATE;
                break;
            case 1:
                layoutView = SEND_TEXT;
                break;
            case 2:
                layoutView = RECEIVE_TEXT;
                break;
            case 3:
                layoutView = SEND_IMAGE;
                break;
            case 4:
                layoutView = RECEIVE_IMAGE;
                break;
            case 5:
                layoutView = SEND_IMAGE_FOR_VIDEO;
                break;
            case 6:
                layoutView = RECEIVE_IMAGE_FOR_VIDEO;
                break;
            case 7:
                layoutView = SEND_VOICE;
                break;
            case 8:
                layoutView = RECEIVE_VOICE;
                break;
            case 9:
                layoutView = SEND_EMOJI;
                break;
            case 10:
                layoutView = RECEIVE_EMOJI;
                break;
            case 11:
                layoutView = SEND_RED_PACKET;
                break;
            case 12:
                layoutView = RECEIVE_RED_PACKET;
                break;
            case 13:
                layoutView = SEND_TRANSFER;
                break;
            case 14:
                layoutView = RECEIVE_TRANSFER;
                break;
            case 15:
                layoutView = SEND_VIDEO;
                break;
            case 16:
                layoutView = RECEIVE_VIDEO;
                break;
            case 17:
                layoutView = SEND_SHARE;
                break;
            case 18:
                layoutView = RECEIVE_SHARE;
                break;
            case 19:
                layoutView = SEND_PERSON_CARD;
                break;
            case 20:
                layoutView = RECEIVE_PERSON_CARD;
                break;
            case 21:
                layoutView = SEND_JOIN_GROUP;
                break;
            case 22:
                layoutView = RECEIVE_JOIN_GROUP;
                break;
            case 23:
                layoutView = SYSTEM_TIPS;
                break;
            default:
                break;
        }
        return layoutView;
    }
}
