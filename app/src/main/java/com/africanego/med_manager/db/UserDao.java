package com.africanego.med_manager.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;
import android.net.Uri;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${sodiqOladeni} on 3/25/2018.
 */

@Dao
public interface UserDao {

    @Query("select * from user_details")
    List<UserDetails> loadUserProfile();

    @Insert
    long insertUserProfile(UserDetails userProfile);

    @Delete
    void deleteUserProfile(UserDetails userProfile);

    @Update
    void updateUserProfile(UserDetails userProfile);


    @Insert
    long insertNewDrugReminder(ReminderEntity reminder);

    @Query("SELECT * FROM " + ReminderEntity.TABLE_NAME)
    List<ReminderEntity> getDrugReminders();

    @Query("SELECT * FROM " + ReminderEntity.TABLE_NAME + " WHERE " + ReminderEntity.DRUG_TAKEN + "= 'true'")
    List<ReminderEntity> getDrugIntakeCount();

    @Query("SELECT * FROM " + ReminderEntity.TABLE_NAME + " WHERE " + ReminderEntity.COLUMN_ID + " = :id")
    ReminderEntity getDrugRemindersById(long id);

    @Query("SELECT COUNT(*) FROM "+ ReminderEntity.TABLE_NAME)
    int count();

    @Query
    ("DELETE FROM " + ReminderEntity.TABLE_NAME + " WHERE " + ReminderEntity.COLUMN_ID + " = :id")
    int deleteDrugReminderById(long id);

    @Query
    ("UPDATE " + ReminderEntity.TABLE_NAME + " SET " + ReminderEntity.DRUG_NAME + "=:drug_name, "
            + ReminderEntity.DRUG_DESC + " =" + " :drug_desc, "
            + ReminderEntity.DRUG_TIME_INTERVAL + " =" + " :drug_time_interval, "
            + ReminderEntity.DRUG_START_TIME + " =" + " :drug_start_time, "
            + ReminderEntity.DRUG_TIME_INTERVAL + " =" + " :drug_end_time, "
            + ReminderEntity.DRUG_TAKEN_COUNT + " =" + " :drug_taken_count"
            + " WHERE " + ReminderEntity.COLUMN_ID + "= :id")
    int updateDrugReminder(long id, String drug_name, String drug_desc, String drug_time_interval,
                           String drug_start_time, String drug_end_time, int drug_taken_count);
}
