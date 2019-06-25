package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.RedRecordInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface RedRecordInfoDao {

    @Query("SELECT * from red_record_info WHERE id= :id")
    RedRecordInfo getItemById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(RedRecordInfo... redRecordInfos);

    @Query("select * from red_record_info WHERE redPackageId= :redId ")
    Flowable<List<RedRecordInfo>> loadRoleInfoList(Long redId);

    @Query("SELECT * from red_record_info WHERE redPackageId= :redId ")
    List<RedRecordInfo> getListByRedId(Long redId);
}
