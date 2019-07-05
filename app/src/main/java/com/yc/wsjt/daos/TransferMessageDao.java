package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.TransferMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface TransferMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TransferMessage transferMessages);

    @Query("select * from wx_transfer_info")
    Flowable<List<TransferMessage>> loadTransMessage();

    @Query("SELECT * from wx_transfer_info WHERE wxMainId= :wxMainId")
    List<TransferMessage> getItemById(int wxMainId);

    @Query("SELECT * from wx_transfer_info WHERE id= :id")
    TransferMessage getItemById(Long id);

    @Query("UPDATE wx_transfer_info SET isReceive = :isReceive WHERE id = :id")
    void updateTransReceive(boolean isReceive, int id);

    @Query("UPDATE wx_transfer_info SET isReceive = :isReceive ,transferType = :type WHERE id = :id")
    void updateTransReceiveAndType(boolean isReceive, int type, int id);
}
