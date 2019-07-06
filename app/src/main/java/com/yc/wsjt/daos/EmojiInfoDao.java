package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.EmojiInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface EmojiInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(EmojiInfo emojiInfo);

    @Query("select * from my_emoji_info")
    Flowable<List<EmojiInfo>> loadEmojiMessage();

    @Query("SELECT * from my_emoji_info order by id desc ")
    List<EmojiInfo> getEmojiList();

    @Query("SELECT * from my_emoji_info WHERE id= :id")
    EmojiInfo getItemById(Long id);
}
