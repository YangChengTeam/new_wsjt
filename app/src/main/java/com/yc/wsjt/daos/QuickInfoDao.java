package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.QuickInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface QuickInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(QuickInfo... quickInfos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertList(List<QuickInfo> list);

    @Query(" select * from quick_info order by addDate asc ")
    Flowable<List<QuickInfo>> loadQuickInfo();

    @Delete
    void deleteQuickInfo(QuickInfo... quickInfo);
}
