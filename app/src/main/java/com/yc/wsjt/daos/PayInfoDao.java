package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.PayInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface PayInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PayInfo payInfo);

    @Query("select * from pay_info")
    Flowable<List<PayInfo>> loadPayInfo();

    @Query("SELECT * from pay_info WHERE id= :id")
    List<PayInfo> getItemById(int id);

    @Query("SELECT * from pay_info WHERE id= :id")
    PayInfo getItemById(Long id);
}
