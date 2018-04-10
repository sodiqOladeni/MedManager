package com.africanego.med_manager.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.africanego.med_manager.R;
import com.africanego.med_manager.db.ReminderEntity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ${sodiqOladeni} on 3/27/2018.
 */

public class CreateAndEditRemainder extends AppCompatActivity {

    private long drugRemindId;
    private EditText drugIntakeInterver, drugNameEdittext, drugDescEdittext, drugStartDate, drugEndDate;
    private TextView remindMeTextView, drugTimeTakenTextView;
    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;
    private String timeIntervalString;
    private int StartAndEndDateFlag;
    private long mTimeInterval;
    private static final int START_DATE_FLAG = 100;
    private static final int END_DATE_FLAG = 200;
    private TextView cancelRemindDialog, okRemindDialog;
    private RadioButton thirtyMinuteButton, oneHourButtton, dailyButtton, weeklyButton, monthlyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drug_reminder);

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        drugNameEdittext = findViewById(R.id.edit_new_drug_name);
        drugDescEdittext = findViewById(R.id.edit_new_drug_desc);
        drugIntakeInterver = findViewById(R.id.edit_new_drug_interval);
        drugStartDate = findViewById(R.id.edit_new_drug_start);
        drugEndDate = findViewById(R.id.edit_new_drug_end);
        remindMeTextView = findViewById(R.id.edit_add_medication_reminder);
        drugTimeTakenTextView = findViewById(R.id.edit_drug_time_taken);

        Bundle intentExtra = getIntent().getExtras();
        if (intentExtra != null){
           drugRemindId = intentExtra.getLong("id");
        }
        Uri intent = getIntent().getData();
        if(intent != null){
            drugRemindId = Long.parseLong(String.valueOf(intent));
        }

        ReminderEntity data = HomeFragment.mUserDatabase.userDao().getDrugRemindersById(drugRemindId);
        String drugName = data.getDrugName();
        String drugDesc = data.getDrugDesc();
        String drugInterval = data.getReminderInterval();
        String drugStartTime = data.getStartDate();
        String drugEndTime = data.getEndDate();
        String drugTimeTaken = String.valueOf(data.getDrugTakenCount());

        drugNameEdittext.setText(drugName);
        drugDescEdittext.setText(drugDesc);
        drugIntakeInterver.setText(drugInterval);
        drugStartDate.setText(drugStartTime);
        drugEndDate.setText(drugEndTime);
        drugTimeTakenTextView.setText(drugTimeTaken);

        drugIntakeInterver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeIntervalDialog();
            }
        });

        drugStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartAndEndDateFlag = START_DATE_FLAG;
                new DatePickerDialog(CreateAndEditRemainder.this, AlertDialog.THEME_HOLO_LIGHT, date,  myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        drugEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartAndEndDateFlag = END_DATE_FLAG;
                new DatePickerDialog(CreateAndEditRemainder.this, AlertDialog.THEME_HOLO_LIGHT, date,  myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateDateLabel() {
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            if (StartAndEndDateFlag == START_DATE_FLAG){
                drugStartDate.setText(sdf.format(myCalendar.getTime()));
            }else {
                drugEndDate.setText(sdf.format(myCalendar.getTime()));

        }
    }

    private void showTimeIntervalDialog() {
        final Dialog customRemindDialog = new Dialog(this);
        customRemindDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customRemindDialog.setContentView(R.layout.dialog_reminder_interval);
        customRemindDialog.setCancelable(true);
        customRemindDialog.show();
        Window window = customRemindDialog.getWindow();
        window.setLayout(android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT);

        thirtyMinuteButton = customRemindDialog.findViewById(R.id.remind_thirty_minutes);
        oneHourButtton = customRemindDialog.findViewById(R.id.remind_one_hour);
        dailyButtton = customRemindDialog.findViewById(R.id.remind_daily);
        weeklyButton = customRemindDialog.findViewById(R.id.remind_weekly);
        monthlyButton = customRemindDialog.findViewById(R.id.remind_monthly);
        cancelRemindDialog = customRemindDialog.findViewById(R.id.remind_cancel);
        okRemindDialog = customRemindDialog.findViewById(R.id.remind_ok);

        thirtyMinuteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeIntervalString = getString(R.string.reminder_thirty_minutes);
            }
        });

        oneHourButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeIntervalString = getString(R.string.remind_one_hour);
            }
        });

        dailyButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeIntervalString = getString(R.string.remind_daily);
            }
        });

        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeIntervalString = getString(R.string.remind_weekly);
            }
        });

        monthlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeIntervalString = getString(R.string.remind_monthly);
            }
        });

        cancelRemindDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customRemindDialog.dismiss();
            }
        });

        okRemindDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeIntervalString == null){
                    Toast.makeText(CreateAndEditRemainder.this, "Time interval must be specified ", Toast.LENGTH_SHORT).show();
                }else {
                    drugIntakeInterver.setText(timeIntervalString);
                    customRemindDialog.dismiss();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_reminder_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "upBack" menu option
            case R.id.home:
                //onBackPressed();
                return true;

            // Respond to a click on the "save" menu option
            case R.id.save_reminder:
                updateDrugReminder();
                Intent homeFragmentIntent = new Intent(this, MedicineList.class);
                startActivity(homeFragmentIntent);
                return true;

            case R.id.delete_reminder:
//                Intent saveProfileIntent = new Intent(this, MedicineList.class);
//                startActivity(saveProfileIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void drugHasBeenTaken(View view) {
      updateDrugReminder();
    }

    public void updateDrugReminder(){

        String drugName = drugNameEdittext.getText().toString();
        String drugDesc = drugDescEdittext.getText().toString();
        String drugInterval = drugIntakeInterver.getText().toString();
        String drugStartTime = drugStartDate.getText().toString();
        String drugEndTime = drugEndDate.getText().toString();
        int drugTimeTaken = Integer.parseInt(drugTimeTakenTextView.getText().toString()) + 1;


        ReminderEntity reminderEntity = new ReminderEntity();
        reminderEntity.setDrugName(drugName);
        reminderEntity.setDrugDesc(drugDesc);
        reminderEntity.setStartDate(drugStartTime);
        reminderEntity.setEndDate(drugEndTime);
        reminderEntity.setReminderInterval(drugInterval);
        reminderEntity.setDrugTakenCount(drugTimeTaken);

        int updateId = HomeFragment.mUserDatabase.userDao().updateDrugReminder(drugRemindId, drugName, drugDesc, drugInterval,
                drugStartTime, drugEndTime, drugTimeTaken);
        if (updateId == 0) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(CreateAndEditRemainder.this, "Unable to save drug", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(CreateAndEditRemainder.this, "Drug saved", Toast.LENGTH_SHORT).show();
        }
//        Intent reminderEditIntent = new Intent(CreateAndEditRemainder.this, MedicineList.class);
//        startActivity(reminderEditIntent);
    }
}
