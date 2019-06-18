package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.RedPackageMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface RedMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(RedPackageMessage... redPackageMessages);

    @Query("select * from wx_red_package_info")
    Flowable<List<RedPackageMessage>> loadRedMessage();

    @Query("SELECT * from wx_red_package_info WHERE wxMainId= :wxMainId")
    List<RedPackageMessage> getItemById(int wxMainId);
}
