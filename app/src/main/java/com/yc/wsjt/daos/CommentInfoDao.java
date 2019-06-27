package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.CommentInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface CommentInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CommentInfo commentInfo);

    @Query("select * from circle_comment_info")
    Flowable<List<CommentInfo>> loadCommentInfo();

    @Query("SELECT * from circle_comment_info WHERE id= :id")
    List<CommentInfo> getItemById(int id);

    @Query("SELECT * from circle_comment_info WHERE id= :id")
    CommentInfo getItemById(Long id);

    @Query("SELECT * from circle_comment_info WHERE circleId= :cid ")
    List<CommentInfo> getListByCId(int cid);

}
