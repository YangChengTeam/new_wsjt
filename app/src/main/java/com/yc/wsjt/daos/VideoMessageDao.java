package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.VideoMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface VideoMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(VideoMessage... videoMessages);

    @Query("select * from wx_video_info")
    Flowable<List<VideoMessage>> loadVideoMessage();

    @Query("SELECT * from wx_video_info WHERE wxMainId= :wxMainId")
    List<VideoMessage> getItemById(int wxMainId);
}
