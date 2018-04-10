package com.africanego.med_manager.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by ${sodiqOladeni} on 3/25/2018.
 */

public class DateTypeConverter {

    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
