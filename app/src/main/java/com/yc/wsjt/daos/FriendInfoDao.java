package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.FriendInfo;
import com.yc.wsjt.bean.MoneyDetail;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface FriendInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(FriendInfo friendInfo);

    @Query("select * from new_friend_info")
    Flowable<List<FriendInfo>> loadFriendList();

    @Query("SELECT * from new_friend_info WHERE id= :id")
    List<FriendInfo> getItemById(int id);

    @Query("SELECT * from new_friend_info WHERE id= :id")
    FriendInfo getItemById(Long id);
}
