package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.TimeMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface TimeMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TimeMessage timeMessages);

    @Query("select * from wx_time_info")
    Flowable<List<TimeMessage>> loadTimeMessage();

    @Query("SELECT * from wx_time_info WHERE wxMainId= :wxMainId")
    List<TimeMessage> getItemById(int wxMainId);
}
