package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.GroupMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface GroupMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(GroupMessage... groupMessages);

    @Query("select * from wx_group_info")
    Flowable<List<GroupMessage>> loadGroupMessage();

    @Query("SELECT * from wx_group_info WHERE wxMainId= :wxMainId")
    List<GroupMessage> getItemById(int wxMainId);

}
