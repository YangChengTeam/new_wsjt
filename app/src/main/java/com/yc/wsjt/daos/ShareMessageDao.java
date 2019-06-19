package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.ShareMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface ShareMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ShareMessage shareMessages);

    @Query("select * from wx_share_info")
    Flowable<List<ShareMessage>> loadShareMessage();

    @Query("SELECT * from wx_share_info WHERE wxMainId= :wxMainId")
    List<ShareMessage> getItemById(int wxMainId);

    @Query("SELECT * from wx_share_info WHERE id= :id")
    ShareMessage getItemById(Long id);
}
