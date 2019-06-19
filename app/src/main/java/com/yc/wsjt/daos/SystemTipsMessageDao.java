package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.SystemTipsMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface SystemTipsMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(SystemTipsMessage systemTipsMessages);

    @Query("select * from wx_system_tips_info")
    Flowable<List<SystemTipsMessage>> loadSystemInfoMessage();

    @Query("SELECT * from wx_system_tips_info WHERE wxMainId= :wxMainId")
    List<SystemTipsMessage> getItemById(int wxMainId);

    @Query("SELECT * from wx_system_tips_info WHERE id= :id")
    SystemTipsMessage getItemById(Long id);
}
