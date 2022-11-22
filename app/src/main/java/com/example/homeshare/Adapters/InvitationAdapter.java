package com.example.homeshare.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homeshare.FeedActivities.InvitationFeedActivity;
import com.example.homeshare.Util.Mail;
import com.example.homeshare.Model.Invitation;
import com.example.homeshare.Model.User;
import com.example.homeshare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvitationAdapter extends RecyclerView.Adapter<InvitationAdapter.ViewHolder> {
    public List<Invitation> data;
    FirebaseStorage storage =FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    private Map<ViewHolder, String> viewHolderToUID = new HashMap<>();
    Context context;
    public InvitationAdapter(List<Invitation> data, Context context){
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public InvitationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view2, parent, false);

        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(InvitationAdapter.ViewHolder holder, int position) {
        Invitation invitation = data.get(position);
        if (invitation.getImageURI() != null) {
            System.out.println("IMAGE URI IS NOT NULL. It is: " + invitation.getImageURI());
            storageReference.child(invitation.getImageURI()).getDownloadUrl().addOnSuccessListener(uri -> {
                if (uri != null) {
                    Glide.with(context).load(uri).into(holder.housingImage);
                }
            });
        }
        holder.invitation = invitation;
        holder.address.setText(invitation.getAddress());
        holder.academicFocus.setText(invitation.getAcademicFocus());
        holder.dailySchedule.setText(invitation.getDailySchedule());
        holder.distance.setText(invitation.getMilesFromCampus().toString() + " miles");
        System.out.println(invitation.getDeadline().toString());

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String date = formatter.format(invitation.getDeadline());
        holder.deadline.setText(date);

        holder.numBeds.setText("" + invitation.getNumBeds());
        holder.otherDetails.setText(invitation.getOtherDetails());
        holder.otherInfo.setText(invitation.getOtherInfo());
        holder.personality.setText(invitation.getPersonality());
        holder.rent.setText("" + String.format("%.2f", invitation.getRent()) + " $/month");
        holder.utilities.setText(invitation.getUtilities());

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
        private ImageButton acceptButton;
        private ImageButton rejectButton;
        private Invitation invitation;
        private TextView distance;
        private ImageView housingImage;


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
            distance = view.findViewById(R.id.distance);
            acceptButton = view.findViewById(R.id.acceptInvitation2);
            rejectButton = view.findViewById(R.id.rejectInvitation2);
            housingImage = view.findViewById(R.id.housingImage);
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
                    System.out.println("CORRECT POSTER DOC " + posterDoc);
                    docData.put("responderRef", responderDoc);
                    docData.put("response", true);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("invitationresponses").add(docData);

                    FirebaseFirestore
                            .getInstance()
                            .document(String.valueOf(posterDoc.getPath()))
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    System.out.println("CHECKPOINT 1: INSIDE METHOD");
                                    DocumentSnapshot posterSnap = task.getResult();
                                    User poster = posterSnap.toObject(User.class);
                                    Mail mail1 = new Mail(poster.getEmail(), poster.getName(),
                                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), "A");
                                    mail1.execute((Object) null);
                                }
                            });

                    Intent intent = new Intent(acceptButton.getContext(), InvitationFeedActivity.class);
                    Toast.makeText(acceptButton.getContext(), "Sent Response to Poster!",
                            Toast.LENGTH_LONG).show();
                    acceptButton.getContext().startActivity(intent);



                } catch (Exception e) {
                    e.printStackTrace();
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
                    Intent intent = new Intent(acceptButton.getContext(), InvitationFeedActivity.class);
                    Toast.makeText(acceptButton.getContext(), "Rejected Invitation",
                            Toast.LENGTH_LONG).show();
                    acceptButton.getContext().startActivity(intent);
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