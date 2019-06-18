package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.TextMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface TextMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(TextMessage... timeMessages);

    @Query("select * from wx_text_info")
    Flowable<List<TextMessage>> loadTextMessage();

    @Query("SELECT * from wx_text_info WHERE wxMainId= :wxMainId")
    List<TextMessage> getItemById(int wxMainId);
}
