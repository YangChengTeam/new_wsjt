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
    List<Long> insert(WeixinChatInfo... users);

    @Delete
    void deleteWeChatInfo(WeixinChatInfo... weixinChatInfos);

    @Query("select * from weixin_chat_info")
    Flowable<List<WeixinChatInfo>> loadWeChatInfo();
}
