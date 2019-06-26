package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.CircleInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface CircleInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CircleInfo circleInfo);

    @Query("select * from circle_info")
    Flowable<List<CircleInfo>> loadCircleInfo();

    @Query("SELECT * from circle_info WHERE id= :id")
    List<CircleInfo> getItemById(int id);

    @Query("SELECT * from circle_info WHERE id= :id")
    CircleInfo getItemById(Long id);

    @Query("SELECT * from circle_info WHERE deviceId= :did ")
    List<CircleInfo> getListByDId(String did);
}
