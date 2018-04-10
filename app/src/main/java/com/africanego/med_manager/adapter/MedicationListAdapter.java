package com.africanego.med_manager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.africanego.med_manager.R;
import com.africanego.med_manager.ui.CreateAndEditRemainder;
import com.africanego.med_manager.db.ReminderEntity;


import java.util.List;

public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.ViewHolder> {

    private Context context;
    private List<ReminderEntity> data;

    public MedicationListAdapter(Context context, List<ReminderEntity> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.list_item_medication, viewGroup,
                        false);
        ViewHolder viewHolder = new ViewHolder(view, context, data);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.drugNameTextView.setText(data.get(position).getDrugName());
        holder.drugDescTextView.setText(data.get(position).getDrugDesc());
        holder.drugEndDateTextView.setText(data.get(position).getEndDate());
        holder.drugRemindIntervalTextView.setText(data.get(position).getReminderInterval());
        holder.drugInitialTextView.setText(String.valueOf(data.get(position).getDrugTakenCount()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView drugInitialTextView, drugNameTextView, drugEndDateTextView, drugDescTextView, drugRemindIntervalTextView;
        private TextView lastname;
        private TextView addFriendButton;
        private List<ReminderEntity> list;
        private Context context;

        public ViewHolder(View view,  Context context, List<ReminderEntity> list) {
            super(view);
            this.list = list;
            this.context = context;
            view.setOnClickListener(this);
            drugInitialTextView = view.findViewById(R.id.drug_initial);
            drugNameTextView = view.findViewById(R.id.list_drug_name);
            drugEndDateTextView = view.findViewById(R.id.list_drug_end_date);
            drugDescTextView = view.findViewById(R.id.list_drug_desc);
            drugRemindIntervalTextView = view.findViewById(R.id.list_drug_remind_interval);

        }

        @Override
        public void onClick(View view) {
            long id = data.get(getAdapterPosition()).getId();
            Intent drugReminderDetailedIntent = new Intent(context, CreateAndEditRemainder.class);
            drugReminderDetailedIntent.putExtra("id", id);
            view.getContext().startActivity(drugReminderDetailedIntent);

        }


    }
}
