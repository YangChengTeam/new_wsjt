package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.PersonMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface PersonMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PersonMessage personMessages);

    @Query("select * from wx_person_info")
    Flowable<List<PersonMessage>> loadTextMessage();

    @Query("SELECT * from wx_person_info WHERE wxMainId= :wxMainId")
    List<PersonMessage> getItemById(int wxMainId);

    @Query("SELECT * from wx_person_info WHERE id= :id")
    PersonMessage getItemById(Long id);
}
