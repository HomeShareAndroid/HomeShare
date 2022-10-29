package com.example.homeshare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.Model.Invitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvitationAdapter extends RecyclerView.Adapter<InvitationAdapter.ViewHolder> {
    private List<Invitation> data;
    private Map<ViewHolder, String> viewHolderToUID = new HashMap<>();
    public InvitationAdapter(List<Invitation> data){
        this.data = data;
    }

    @NonNull
    @Override
    public InvitationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(InvitationAdapter.ViewHolder holder, int position) {
        Invitation invitation = data.get(position);
        holder.invitation = invitation;
        holder.address.setText("Address: " + invitation.getAddress());
        holder.academicFocus.setText("Academic Focus of Poster: " + invitation.getAcademicFocus());
        holder.dailySchedule.setText("Poster's Daily Schedule: " + invitation.getDailySchedule());
        holder.deadline.setText("Deadline to Respond: " + invitation.getDeadline().toString());
        holder.numBeds.setText("Number of Beds Available: " + invitation.getNumBeds());
        holder.otherDetails.setText("Other Housing Details: " + invitation.getOtherDetails());
        holder.otherInfo.setText("Other Info about the Poster: " + invitation.getOtherInfo());
        holder.personality.setText("Poster's Personality: " + invitation.getPersonality());
        holder.rent.setText("Rent Details: " + invitation.getRent());
        holder.utilities.setText("Utilities Details: " +invitation.getUtilities());
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        private Button acceptButton;
        private Button rejectButton;
        private Invitation invitation;

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
            acceptButton = view.findViewById(R.id.acceptInvitation);
            rejectButton = view.findViewById(R.id.rejectInvitation);
            acceptButton.setOnClickListener(v -> {
                try {
                    DocumentReference posterDoc = FirebaseFirestore
                            .getInstance()
                            .collection("users")
                            .document(invitation.getPosterUid());
                    DocumentReference responderDoc = FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(FirebaseAuth.getInstance().getUid());
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("invitationRef", InvitationFeedActivity.invToRef.get(invitation));
                    docData.put("posterRef", posterDoc);
                    docData.put("responderRef", responderDoc);
                    docData.put("response", true);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("invitationresponses").add(docData);
                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Something went wrong accepting invitation");
                }
            });
            rejectButton.setOnClickListener(v -> {
                try {
                    DocumentReference posterDoc = FirebaseFirestore
                            .getInstance()
                            .collection("users")
                            .document(invitation.getPosterUid());
                    DocumentReference responderDoc = FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(FirebaseAuth.getInstance().getUid());
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("invitationRef", InvitationFeedActivity.invToRef.get(invitation));
                    docData.put("posterRef", posterDoc);
                    docData.put("responderRef", responderDoc);
                    docData.put("response", false);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("invitationresponses").add(docData);


                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Something went wrong rejecting invitation");
                }

            });
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Must Click Accept or Reject", Toast.LENGTH_SHORT).show();
        }
    }
}