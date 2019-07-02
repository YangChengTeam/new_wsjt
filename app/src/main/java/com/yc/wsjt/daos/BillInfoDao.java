package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.BillInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface BillInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(BillInfo moneyDetail);

    @Query("select * from bill_info")
    Flowable<List<BillInfo>> loadMoneyMessage();

    @Query("SELECT * from bill_info order by billDate asc ")
    List<BillInfo> getBillList();

    @Query("SELECT * from bill_info WHERE id= :id")
    BillInfo getItemById(Long id);
}
