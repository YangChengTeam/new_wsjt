package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.RoleInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface RoleInfoDao {

    @Query("SELECT * from chat_role_info WHERE qunLiaoId= :did")
    RoleInfo getItemById(String did);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(RoleInfo... messageContents);

    @Query("select * from chat_role_info WHERE qunLiaoId= :qunLiaoId ")
    Flowable<List<RoleInfo>> loadRoleInfoList(Long qunLiaoId);

    @Query("SELECT * from chat_role_info WHERE qunLiaoId= :qunLiaoId ")
    List<RoleInfo> getListById(Long qunLiaoId);
}
