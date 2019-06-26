package com.yc.wsjt.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.CircleBaseSetInfo;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/14.
 */
@Dao
public interface CircleBaseSetInfoDao {

    @Query("SELECT * from circle_base_set_info WHERE deviceId= :did")
    CircleBaseSetInfo getItemById(String did);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CircleBaseSetInfo circleBaseSetInfo);

    @Query("SELECT * from circle_base_set_info")
    Flowable<List<CircleBaseSetInfo>> loadCircleBaseInfo();

    @Query("SELECT * FROM circle_base_set_info WHERE deviceId = :deviceId ")
    Flowable<CircleBaseSetInfo> getCircleBaseInfoByDId(String deviceId);

    @Query("UPDATE circle_base_set_info SET coverImage = :coverBg WHERE id = :id")
    void updateBgImage(String coverBg, int id);

    @Query("UPDATE circle_base_set_info SET unreadCount = :unreadcount WHERE id = :id")
    void updateUnReadCount(String unreadcount, int id);

    @Query("UPDATE circle_base_set_info SET roleName = :roleName, roleHead = :roleHead WHERE id = :id")
    void updateRole(String roleName,String roleHead,int id);

    @Query("UPDATE circle_base_set_info SET unreadUserName = :unreadName, unreadUserHead = :unReadHead WHERE id = :id")
    void updateUnReadUserInfo(String unreadName,String unReadHead,int id);
}
