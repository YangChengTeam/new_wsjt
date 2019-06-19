package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.AudioMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface AudioMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(AudioMessage audioMessage);

    @Query("select * from wx_audio_info")
    Flowable<List<AudioMessage>> loadAudioMessage();

    @Query("SELECT * from wx_audio_info WHERE wxMainId= :wxMainId")
    List<AudioMessage> getItemById(int wxMainId);

    @Query("SELECT * from wx_audio_info WHERE id= :id")
    AudioMessage getItemById(Long id);
}
