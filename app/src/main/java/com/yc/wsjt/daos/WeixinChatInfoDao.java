package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.WeixinChatInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface WeixinChatInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(WeixinChatInfo users);

    @Delete
    void deleteWeChatInfo(WeixinChatInfo... weixinChatInfos);

    @Query("select * from weixin_chat_info where mainId = :mainId")
    Flowable<List<WeixinChatInfo>> loadWeChatInfo(int mainId);

    @Query("SELECT * from weixin_chat_info WHERE id= :id")
    WeixinChatInfo getItemById(Long id);

    @Query("DELETE from weixin_chat_info where mainId = :mainId")
    void deleteAllByMainId(int mainId);
}
