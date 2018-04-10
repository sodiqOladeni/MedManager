package com.africanego.med_manager.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.africanego.med_manager.R;
import com.africanego.med_manager.adapter.MedicationListAdapter;
import com.africanego.med_manager.db.ReminderEntity;

import java.util.List;

public class MonthIntake extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_month_intake, container, false);

        mRecyclerView = rootView.findViewById(R.id.mi_list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = rootView.findViewById(R.id.mi_empty_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ReminderEntity> data = HomeFragment.mUserDatabase.userDao().getDrugIntakeCount();
        if (data != null && !data.isEmpty()) {
            mRecyclerView.setAdapter(new MedicationListAdapter(getContext(), data));
            emptyView.setVisibility(View.GONE);
        }else{
            emptyView.setVisibility(View.VISIBLE);
        }

       return rootView;
    }
}
