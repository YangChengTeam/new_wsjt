package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.WeixinChatInfo;
import com.yc.wsjt.bean.WeixinQunChatInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface WeixinQunChatInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(WeixinQunChatInfo users);

    @Delete
    void deleteQunWeChatInfo(WeixinQunChatInfo... weixinChatInfos);

    @Query("select * from weixin_qun_chat_info where wxMainId = :wxMainId")
    Flowable<List<WeixinQunChatInfo>> loadQunWeChatInfo(int wxMainId);

    @Query("SELECT * from weixin_qun_chat_info WHERE id= :id")
    WeixinQunChatInfo getItemById(Long id);
}
