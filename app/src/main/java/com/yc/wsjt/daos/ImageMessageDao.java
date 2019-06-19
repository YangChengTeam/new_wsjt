package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.ImageMessage;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface ImageMessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(ImageMessage timeMessages);

    @Query("select * from wx_image_info")
    Flowable<List<ImageMessage>> loadImageMessage();

    @Query("SELECT * from wx_image_info WHERE wxMainId= :wxMainId")
    List<ImageMessage> getItemById(int wxMainId);

    @Query("SELECT * from wx_image_info WHERE id= :id")
    ImageMessage getItemById(Long id);
}
