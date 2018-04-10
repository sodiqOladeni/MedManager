package com.africanego.med_manager.db;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by ${sodiqOladeni} on 3/25/2018.
 */

@Database(entities = {UserDetails.class, ReminderEntity.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();
//    public abstract UserDetails userDetailsDao();
    private static UserDatabase sInstance;

    public static synchronized UserDatabase getInstance(Context context){
        if (sInstance == null){
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "user_medication_db").allowMainThreadQueries().build();
            sInstance.populateInitialData();
        }
        return sInstance;
    }

    private void populateInitialData() {
        if (userDao().count() == 0){
            ReminderEntity newReminder = new ReminderEntity();
            beginTransaction();
            try{
//                for (int i = 0 < ReminderEntity.TABLE_NAME.length; i++){
//                    newReminder.na = ReminderEntity.TABLE_NAME[i];
//                    userDao().insertNewDrugReminder(newReminder)
//                }
                setTransactionSuccessful();
            }finally {
                endTransaction();
            }
        }
    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
