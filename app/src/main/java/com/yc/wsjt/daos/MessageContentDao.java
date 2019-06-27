package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.MessageContent;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface MessageContentDao {

    @Query("SELECT * from wx_chat_info_main WHERE deviceId = :did and modelType = :modelType")
    MessageContent getItemById(String did, int modelType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(MessageContent messageContents);

    @Query("select * from wx_chat_info_main")
    Flowable<List<MessageContent>> loadMessageContent();

    @Query("SELECT * FROM wx_chat_info_main WHERE deviceId = :deviceId ")
    Flowable<MessageContent> getMessageContentByDeviceId(String deviceId);
}
