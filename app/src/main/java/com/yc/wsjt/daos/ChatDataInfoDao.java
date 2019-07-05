package com.yc.wsjt.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.ChatDataInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/14.
 */
@Dao
public interface ChatDataInfoDao {

    @Query("SELECT * from chat_data_info WHERE deviceId= :did and modelType = :modelType")
    ChatDataInfo getItemById(String did, int modelType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(ChatDataInfo... chatDataInfo);

    @Query("UPDATE chat_data_info SET personName = :personName, personHead = :personHead, otherPersonName = :otherPersonName,otherPersonHead = :otherPersonHead WHERE id = :id")
    void update(String personName, String personHead, String otherPersonName, String otherPersonHead, int id);

    @Query("UPDATE chat_data_info SET personName = :personName, personHead = :personHead WHERE id = :id")
    void updateSelf(String personName, String personHead ,int id);

    @Query("UPDATE chat_data_info SET otherPersonName = :otherPersonName,otherPersonHead = :otherPersonHead WHERE id = :id")
    void updateOther(String otherPersonName, String otherPersonHead, int id);

    @Query("SELECT * from chat_data_info")
    Flowable<List<ChatDataInfo>> loadChatDataInfo();

    @Query("SELECT * FROM chat_data_info WHERE deviceId = :deviceId ")
    Flowable<ChatDataInfo> getChatDataByDeviceId(String deviceId);

    @Query("UPDATE chat_data_info SET chatBgImage = :bgImage WHERE id = :id")
    void updateBgImage(String bgImage, int id);

    @Query("UPDATE chat_data_info SET friendType = :friendType WHERE id = :id")
    void updateFriendState(int friendType, int id);

    @Query("UPDATE chat_data_info SET messageDisturb = :state WHERE id = :id")
    void updateMessageState(boolean state, int id);

    @Query("UPDATE chat_data_info SET receiverOpen = :state WHERE id = :id")
    void updateReceiverState(boolean state, int id);

    @Query("UPDATE chat_data_info SET showWeiXinMoney = :state WHERE id = :id")
    void updateMoneyState(boolean state, int id);
}
