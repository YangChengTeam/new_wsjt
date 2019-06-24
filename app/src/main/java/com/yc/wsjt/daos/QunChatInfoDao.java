package com.yc.wsjt.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.ui.activity.QunChatInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/14.
 */
@Dao
public interface QunChatInfoDao {

    @Query("SELECT * from qun_chat_info WHERE deviceId= :did")
    QunChatInfo getItemById(String did);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(QunChatInfo qunChatInfos);

    @Query("SELECT * from qun_chat_info")
    Flowable<List<QunChatInfo>> loadQunChatInfo();

    @Query("SELECT * FROM qun_chat_info WHERE deviceId = :deviceId ")
    Flowable<QunChatInfo> getQunChatDataByDeviceId(String deviceId);

    @Query("UPDATE qun_chat_info SET chatInfoBg = :bgImage WHERE id = :id")
    void updateBgImage(String bgImage, Long id);

    @Query("UPDATE qun_chat_info SET qunLiaoName = :qunName WHERE id = :id")
    void updateQunName(String qunName, Long id);

    @Query("UPDATE qun_chat_info SET chatPersonNumber = :number WHERE id = :id")
    void updateQunNumber(String number, Long id);

    @Query("UPDATE qun_chat_info SET isOpenDisturb = :disturb WHERE id = :id")
    void updateDisturb(boolean disturb, Long id);

    @Query("UPDATE qun_chat_info SET openNickName = :opennickname WHERE id = :id")
    void updateOpenNickName(boolean opennickname, Long id);
}
