package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.MoneyDetail;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface MoneyDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(MoneyDetail moneyDetail);

    @Query("select * from money_detail")
    Flowable<List<MoneyDetail>> loadMoneyMessage();

    @Query("SELECT * from money_detail WHERE id= :id")
    List<MoneyDetail> getItemById(int id);

    @Query("SELECT * from money_detail WHERE id= :id")
    MoneyDetail getItemById(Long id);
}
