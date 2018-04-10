package com.africanego.med_manager.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;
import android.support.annotation.ColorInt;

import java.util.Date;

/**
 * Created by ${sodiqOladeni} on 3/25/2018.
 */

@Entity(tableName = ReminderEntity.TABLE_NAME)
public class ReminderEntity {

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String TABLE_NAME = "drug_reminder";
    public static final String DRUG_NAME = "drug_name";
    public static final String DRUG_DESC = "drug_desc";
    public static final String DRUG_TIME_INTERVAL = "reminder_time_interval";
    public static final String DRUG_START_TIME = "drug_start_date";
    public static final String DRUG_END_TIME = "drug_end_date";
    public static final String DRUG_TAKEN = "drug_taken";
    public static final String DRUG_TAKEN_COUNT = "drug_taken_count";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = ReminderEntity.DRUG_NAME)
    private String drugName;

    @ColumnInfo(name = ReminderEntity.DRUG_DESC)
    private String drugDesc;

    @ColumnInfo(name = ReminderEntity.DRUG_TIME_INTERVAL)
    private String reminderInterval;

    @ColumnInfo(name = ReminderEntity.DRUG_START_TIME)
    private String startDate;

    @ColumnInfo(name = ReminderEntity.DRUG_END_TIME)
    private String endDate;

    @ColumnInfo(name = ReminderEntity.DRUG_TAKEN)
    private String hasDrugTaken;

    @ColumnInfo(name = ReminderEntity.DRUG_TAKEN_COUNT)
    private int drugTakenCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugDesc() {
        return drugDesc;
    }

    public void setDrugDesc(String drugDesc) {
        this.drugDesc = drugDesc;
    }

    public String getReminderInterval() {
        return reminderInterval;
    }

    public void setReminderInterval(String reminderInterval) {
        this.reminderInterval = reminderInterval;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHasDrugTaken() {
        return hasDrugTaken;
    }

    public void setHasDrugTaken(String hasDrugTaken) {
        this.hasDrugTaken = hasDrugTaken;
    }

    public int getDrugTakenCount() {
        return drugTakenCount;
    }

    public void setDrugTakenCount(int drugTakenCount) {
        this.drugTakenCount = drugTakenCount;
    }
}
