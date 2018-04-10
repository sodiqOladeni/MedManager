package com.africanego.med_manager.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ${sodiqOladeni} on 3/27/2018.
 */

@Entity(tableName = MonthlySummaryEntity.TABLE_NAME)
public class MonthlySummaryEntity {

    public static final String TABLE_NAME = "drug_summary";
    public static final String DRUG_NAME = "drug_summary";
    public static final String DRUG_DESC = "drug_summary";
    public static final String DRUG_INTAKE_TIME = "drug_summary";

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = MonthlySummaryEntity.DRUG_NAME)
    private String drugName;

    @ColumnInfo(name = MonthlySummaryEntity.DRUG_DESC)
    private String drugDesc;

    @ColumnInfo(name = MonthlySummaryEntity.DRUG_INTAKE_TIME)
    private int drugIntakeTime;
}
