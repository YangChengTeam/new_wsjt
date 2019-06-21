package com.yc.wsjt.common;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.yc.wsjt.bean.AudioMessage;
import com.yc.wsjt.bean.ChatDataInfo;
import com.yc.wsjt.bean.EmojiMessage;
import com.yc.wsjt.bean.FriendInfo;
import com.yc.wsjt.bean.GroupMessage;
import com.yc.wsjt.bean.ImageMessage;
import com.yc.wsjt.bean.MessageContent;
import com.yc.wsjt.bean.MoneyDetail;
import com.yc.wsjt.bean.PayInfo;
import com.yc.wsjt.bean.PersonMessage;
import com.yc.wsjt.bean.QuickInfo;
import com.yc.wsjt.bean.RedPackageMessage;
import com.yc.wsjt.bean.ShareMessage;
import com.yc.wsjt.bean.SystemTipsMessage;
import com.yc.wsjt.bean.TextMessage;
import com.yc.wsjt.bean.TimeMessage;
import com.yc.wsjt.bean.TransferMessage;
import com.yc.wsjt.bean.VideoMessage;
import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.daos.AudioMessageDao;
import com.yc.wsjt.daos.ChatDataInfoDao;
import com.yc.wsjt.daos.EmojiMessageDao;
import com.yc.wsjt.daos.FriendInfoDao;
import com.yc.wsjt.daos.GroupMessageDao;
import com.yc.wsjt.daos.ImageMessageDao;
import com.yc.wsjt.daos.MessageContentDao;
import com.yc.wsjt.daos.MoneyDetailDao;
import com.yc.wsjt.daos.PayInfoDao;
import com.yc.wsjt.daos.PersonMessageDao;
import com.yc.wsjt.daos.QuickInfoDao;
import com.yc.wsjt.daos.RedMessageDao;
import com.yc.wsjt.daos.ShareMessageDao;
import com.yc.wsjt.daos.SystemTipsMessageDao;
import com.yc.wsjt.daos.TextMessageDao;
import com.yc.wsjt.daos.TimeMessageDao;
import com.yc.wsjt.daos.TransferMessageDao;
import com.yc.wsjt.daos.VideoMessageDao;
import com.yc.wsjt.daos.WeixinChatInfoDao;

@Database(entities = {
        WeixinChatInfo.class,
        ChatDataInfo.class,
        QuickInfo.class,
        MessageContent.class,
        TimeMessage.class,
        TextMessage.class,
        ImageMessage.class,
        AudioMessage.class,
        EmojiMessage.class,
        RedPackageMessage.class,
        TransferMessage.class,
        VideoMessage.class,
        ShareMessage.class,
        PersonMessage.class,
        GroupMessage.class,
        SystemTipsMessage.class,
        MoneyDetail.class,
        FriendInfo.class,
        PayInfo.class}, version = 1, exportSchema = false)


public abstract class AppDatabase extends RoomDatabase {

    public abstract WeixinChatInfoDao weixinChatInfoDao();

    public abstract ChatDataInfoDao chatDataInfoDao();

    public abstract QuickInfoDao quickInfoDao();

    public abstract MessageContentDao messageContentDao();

    public abstract TimeMessageDao timeMessageDao();

    public abstract TextMessageDao textMessageDao();

    public abstract ImageMessageDao imageMessageDao();

    public abstract AudioMessageDao audioMessageDao();

    public abstract EmojiMessageDao emojiMessageDao();

    public abstract RedMessageDao redMessageDao();

    public abstract TransferMessageDao transferMessageDao();

    public abstract VideoMessageDao videoMessageDao();

    public abstract ShareMessageDao shareMessageDao();

    public abstract PersonMessageDao personMessageDao();

    public abstract GroupMessageDao groupMessageDao();

    public abstract SystemTipsMessageDao systemTipsMessageDao();

    public abstract MoneyDetailDao moneyDetailDao();

    public abstract FriendInfoDao friendInfoDao();

    public abstract PayInfoDao payInfoDao();
}
