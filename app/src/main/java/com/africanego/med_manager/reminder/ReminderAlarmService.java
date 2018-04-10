package com.africanego.med_manager.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.africanego.med_manager.R;
import com.africanego.med_manager.ui.CreateAndEditRemainder;
import com.africanego.med_manager.ui.HomeFragment;
import com.africanego.med_manager.db.ReminderEntity;

/**
 * Created by sodiqOladeni on 9/22/17.
 */

public class ReminderAlarmService extends IntentService {
    private static final String TAG = ReminderAlarmService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 42;
    private static final String NOTIFICATION_CHANNEL = "reminder_channel";
    //This is a deep link intent, and needs the task stack

    public static PendingIntent getReminderPendingIntent(Context context, long alarmId) {
        Intent action = new Intent(context, ReminderAlarmService.class);
        action.putExtra("id", alarmId);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public ReminderAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        long alarmId = intent.getExtras().getLong("id");

        //Display a notification to view the task details
        Intent action = new Intent(this, CreateAndEditRemainder.class);
        action.setData(Uri.parse(String.valueOf(alarmId)));
        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //Grab the task description
        ReminderEntity reminderEntity = HomeFragment.mUserDatabase.userDao().getDrugRemindersById(alarmId);
        String title = reminderEntity.getDrugName();
        String description = reminderEntity.getDrugDesc();

        Notification note = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setContentTitle("Time to take "+ title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_delete_black_24dp)
                .setContentIntent(operation)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true)
                .build();

        manager.notify(NOTIFICATION_ID, note);
    }
}