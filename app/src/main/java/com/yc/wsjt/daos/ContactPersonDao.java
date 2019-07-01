package com.yc.wsjt.daos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.wsjt.bean.ContactPerson;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zhangdinghui on 2019/5/7.
 */

@Dao
public interface ContactPersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(ContactPerson... contactPerson);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertList(List<ContactPerson> list);

    @Query(" select * from contact_person order by id asc ")
    Flowable<List<ContactPerson>> loadPerson();

    @Query("SELECT * from contact_person")
    List<ContactPerson> getPersonList();

    @Delete
    void deletePerson(ContactPerson... contactPerson);
}
