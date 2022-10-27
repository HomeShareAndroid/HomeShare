package com.example.homeshare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.Model.Invitation;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Invitation> data;
    public CustomAdapter (List<Invitation> data){
        this.data = data;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        Invitation invitation = data.get(position);
        holder.address.setText(invitation.getAddress());
        holder.academicFocus.setText(invitation.getAcademicFocus());
        holder.dailySchedule.setText(invitation.getDailySchedule());
        holder.deadline.setText(invitation.getDeadline().toString());
        holder.numBeds.setText(invitation.getNumBeds());
        holder.otherDetails.setText(invitation.getOtherDetails());
        holder.otherInfo.setText(invitation.getOtherInfo());
        holder.personality.setText(invitation.getPersonality());
        holder.rent.setText(invitation.getRent());
        holder.utilities.setText(invitation.getUtilities());
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private TextView address;
        private TextView academicFocus;
        private TextView dailySchedule;
        private TextView deadline;
        private TextView numBeds;
        private TextView otherDetails;
        private TextView otherInfo;
        private TextView personality;
        private TextView rent;
        private TextView utilities;

        public ViewHolder(View view) {
            super(view);
            address = view.findViewById(R.id.address);
            academicFocus = view.findViewById(R.id.academicFocus);
            dailySchedule = view.findViewById(R.id.dailySchedule);
            deadline = view.findViewById(R.id.deadline);
            numBeds = view.findViewById(R.id.numBeds);
            otherDetails = view.findViewById(R.id.otherDetails);
            otherInfo = view.findViewById(R.id.otherInfo);
            personality = view.findViewById(R.id.personality);
            rent = view.findViewById(R.id.rent);
            utilities = view.findViewById(R.id.utilities);
            view.setOnClickListener(this);
            //this.textView = view.findViewById(R.id.textview);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "poster Uid : " + this.textView.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}