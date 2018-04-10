package com.africanego.med_manager.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.africanego.med_manager.R;
import com.africanego.med_manager.adapter.MedicationListAdapter;
import com.africanego.med_manager.db.ReminderEntity;
import com.africanego.med_manager.db.UserDatabase;
import com.africanego.med_manager.db.UserDetails;
import com.africanego.med_manager.reminder.AlarmScheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{

    private EditText drugNameEdittext, drugDescEdittext, drugIntakeInterver, drugStartDate, drugEndDate;
    private TextView remindMeTextView, cancelDialogTextView, cancelRemindDialog, okRemindDialog;
    private RadioButton thirtyMinuteButton, oneHourButtton, dailyButtton, weeklyButton, monthlyButton;
    private int reminderTime;
    private View emptyView;
    private Calendar mCalendar;
    private ImageView cancelDialogImageView;
    private int mYear, mMonth, mHour, mMinute, mDay, drugTakenCount;
    private String mTime, mDate, timeIntervalString, drugHasBeenTaken;
    private LinearLayout drugTakenBefore, amountTaken;
    private Spinner drugTakenBeforeSpinner, drugTakenTimeSpinner;
    private DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar = Calendar.getInstance();
    private int StartAndEndDateFlag;
    private long mTimeInterval, selectedTimestamp;
    private static final int START_DATE_FLAG = 100;
    private static final int END_DATE_FLAG = 200;
    public static UserDatabase mUserDatabase;
    private static final String TAG = MedicineList.class.getSimpleName();
    MedicationListAdapter mReminderAdapter;
    private RecyclerView mlRecyclerview;
    private HomeFragment homeFragment;
    private LinearLayoutManager linearLayoutManager;
    private static final int LOADER_MENU = 1000;

    //Constant for the time

    private static final long ONE_MINUTES = 60000L;
    private static final long THIRTY_MINUTES = 1800000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long DAILY = 86400000L;
    private static final long WEEKLY = 604800000L;
    private static final long MONTHLY = 2419200000L;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);


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

        mUserDatabase = Room.databaseBuilder(getContext(),
                UserDatabase.class, "drug_reminder_db").allowMainThreadQueries().build();

        emptyView = rootView.findViewById(R.id.empty_view);
        mlRecyclerview = rootView.findViewById(R.id.ml_recyclerView);

        linearLayoutManager = new LinearLayoutManager(getContext());
        mlRecyclerview.setLayoutManager(linearLayoutManager);

        List<ReminderEntity> data = mUserDatabase.userDao().getDrugReminders();
        if (data != null && !data.isEmpty()) {
            mlRecyclerview.setAdapter(new MedicationListAdapter(getContext(), data));
            emptyView.setVisibility(View.GONE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
        }


        mHour = myCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = myCalendar.get(Calendar.MINUTE);
        mYear = myCalendar.get(Calendar.YEAR);
        mMonth = myCalendar.get(Calendar.MONTH) + 1;
        mDay = myCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        return rootView;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Share" menu option
            case R.id.add_medicatation:
                showAddToMedicationDialog();
                return true;
            case R.id.share:
                return true;
            case R.id.help:
                Intent helpIntent = new Intent(getContext(), AppHelp.class);
                startActivity(helpIntent);
                return true;
            case R.id.about:
                Intent aboutIntent = new Intent(getContext(), AppAbout.class);
                startActivity(aboutIntent);
                return true;
            case R.id.settings:
                Intent settingsIntent = new Intent(getContext(), AppSettings.class);
                startActivity(settingsIntent);
                return true;
            case R.id.delete_all:
                deleteAllMedicationFromDatabase();
                return true;
            case R.id.logout:
                 //mUserDatabase.userDao().deleteUserProfile();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllMedicationFromDatabase() {
    }

    private void showAddToMedicationDialog() {
        final Dialog customDialog = new Dialog(getContext());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_add_med);
        customDialog.setCancelable(true);
        customDialog.show();
        Window window = customDialog.getWindow();
        window.setLayout(android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT,
                android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT);


        drugNameEdittext = customDialog.findViewById(R.id.new_drug_name);
        drugDescEdittext = customDialog.findViewById(R.id.new_drug_desc);
        drugIntakeInterver = customDialog.findViewById(R.id.new_drug_interval);
        drugStartDate = customDialog.findViewById(R.id.new_drug_start);
        drugEndDate = customDialog.findViewById(R.id.new_drug_end);
        remindMeTextView = customDialog.findViewById(R.id.add_medication_reminder);
        cancelDialogTextView = customDialog.findViewById(R.id.not_ready_medication);
        cancelDialogImageView = customDialog.findViewById(R.id.cancel_new_med_dialog);
        drugTakenBefore = customDialog.findViewById(R.id.new_already_taken_drug);
        amountTaken = customDialog.findViewById(R.id.new_drug_in_taken_pieces);
        drugTakenBeforeSpinner = customDialog.findViewById(R.id.spinner_question);
        drugTakenTimeSpinner = customDialog.findViewById(R.id.spinner_amount);

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
                new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                // Set up calender for creating the notification
                mCalendar = Calendar.getInstance();
                mCalendar.set(Calendar.MONTH, --mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar.set(Calendar.MINUTE, mMinute);
                mCalendar.set(Calendar.SECOND, 0);

                selectedTimestamp =  mCalendar.getTimeInMillis();
            }
        });

        drugEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartAndEndDateFlag = END_DATE_FLAG;
                new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, date,  myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        drugTakenBeforeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String userAnswer = drugTakenBeforeSpinner.getSelectedItem().toString();
                if (userAnswer.equals("Yes")){
                    drugHasBeenTaken = "true";
                    amountTaken.setVisibility(View.VISIBLE);
                }else{
                    drugHasBeenTaken = "false";
                    drugTakenTimeSpinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        drugTakenTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String userTimeTakenSelected = drugTakenTimeSpinner.getSelectedItem().toString();

                if (userTimeTakenSelected == null || userTimeTakenSelected.isEmpty()){
                    drugTakenCount = 0;
                }else {
                    drugTakenCount = Integer.parseInt(userTimeTakenSelected);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        remindMeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drugName = drugNameEdittext.getText().toString();
                String drugDesc = drugDescEdittext.getText().toString();
                String drugInterval = drugIntakeInterver.getText().toString();
                String drugStart = drugStartDate.getText().toString();
                String drugEnd = drugEndDate.getText().toString();

//                if (drugName.isEmpty() || drugDesc.isEmpty() || drugInterval.isEmpty()
//                        || drugStart.isEmpty() || drugEnd.isEmpty()){
//
//                    Toast.makeText(MedicineList.this, "All ", Toast.LENGTH_SHORT).show();
//                } else{

                if (drugInterval.equals(getString(R.string.reminder_thirty_minutes))) {
                    mTimeInterval = Integer.parseInt("100") * THIRTY_MINUTES;
                } else if (drugInterval.equals(getString(R.string.remind_one_hour))) {
                    mTimeInterval = Integer.parseInt("100") * ONE_HOUR;
                } else if (drugInterval.equals(getString(R.string.remind_daily))) {
                    mTimeInterval = Integer.parseInt("100") * DAILY;
                } else if (drugInterval.equals(getString(R.string.remind_weekly))) {
                    mTimeInterval = Integer.parseInt("100") * WEEKLY;
                } else if (drugInterval.equals(getString(R.string.remind_monthly))) {
                    mTimeInterval = Integer.parseInt("100") * MONTHLY;
                }

                ReminderEntity drugRemind = new ReminderEntity();
                drugRemind.setDrugName(drugName);
                drugRemind.setDrugDesc(drugDesc);
                drugRemind.setStartDate(drugStart);
                drugRemind.setEndDate(drugEnd);
                drugRemind.setReminderInterval(String.valueOf(mTimeInterval));
                drugRemind.setHasDrugTaken(drugHasBeenTaken);
                drugRemind.setDrugTakenCount(drugTakenCount);
                long insertRow = mUserDatabase.userDao().insertNewDrugReminder(drugRemind);

                if (insertRow == 0) {
                    // If the new content URI is null, then there was an error with insertion.
                    Toast.makeText(getContext(), "Unable to save drug", Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the insertion was successful and we can display a toast.
                    Toast.makeText(getContext(), "Drug saved", Toast.LENGTH_SHORT).show();
                }

                new AlarmScheduler().setRepeatAlarm(getContext(), selectedTimestamp, insertRow, 60000L);

                List<ReminderEntity> data = mUserDatabase.userDao().getDrugReminders();
                mlRecyclerview.setAdapter(new MedicationListAdapter(getContext(), data));
                emptyView.setVisibility(View.GONE);
                customDialog.dismiss();

                Toast.makeText(getContext(), "Reminder saved "+ insertRow, Toast.LENGTH_SHORT).show();
            }
        });
        cancelDialogTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        cancelDialogImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }


    private void showTimeIntervalDialog() {
        final Dialog customRemindDialog = new Dialog(getContext());
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
                    Toast.makeText(getContext(), "Time interval must be specified ", Toast.LENGTH_SHORT).show();
                }else {
                    drugIntakeInterver.setText(timeIntervalString);
                    customRemindDialog.dismiss();
                }
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

//        newText = newText.toLowerCase();
//        List<ReminderEntity> newDataList = mUserDatabase.userDao().getDrugReminders();
//
//        for (ReminderEntity reminderEntity :  newDataList){
//            String name = reminderEntity.getDrugName().toLowerCase();
//            if (name.contains(newText)){
//                newDataList.add(reminderEntity);
//            }
//
//            mReminder.setFilter(newDataList);
//        }
        return true;
    }
}
