package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.EmojiMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface EmojiMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(EmojiMessage... emojiMessages);

    @Query("select * from wx_emoji_info")
    Flowable<List<EmojiMessage>> loadEmojiMessage();

    @Query("SELECT * from wx_emoji_info WHERE wxMainId= :wxMainId")
    List<EmojiMessage> getItemById(int wxMainId);
}
