package com.example.homeshare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.Model.Invitation;
import com.example.homeshare.Model.InvitationResponse;
import com.example.homeshare.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ViewHolder> {
    private List<InvitationResponse> data;
    private Map<ViewHolder, String> viewHolderToUID = new HashMap<>();
    private static Context con;
    public ResponseAdapter(Context con, List<InvitationResponse> data){
        System.out.println("Making Response Feed with this many items: " + data.size());
        this.con = con;
        this.data = data;
    }

    @Override
    public ResponseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_item_view, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(ResponseAdapter.ViewHolder holder, int position) {
        InvitationResponse response = data.get(position);
        holder.response = response;

        response.getInvitationRef().get().addOnSuccessListener(documentSnapshot -> {
            holder.address.setText("Property: " + documentSnapshot.toObject(Invitation.class).getAddress());
            response.getResponderRef().get().addOnSuccessListener(documentSnapshot1 -> {
                User responder = documentSnapshot1.toObject(User.class);
                holder.responderName.setText("Responder's Name: " +responder.getName());
                holder.responderUid.setText("Responder's Id: " + responder.getUid());
                holder.responderEmail.setText("Responder's Email: " + responder.getEmail());
            });
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView address;
        private TextView responderName;
        private TextView responderEmail;
        private TextView responderUid;
        private Button acceptButton;
        private Button rejectButton;
        private InvitationResponse response;
        private Button visitProfile;

        public ViewHolder(View view) {
            super(view);
            address = view.findViewById(R.id.response_address);
            responderName = view.findViewById(R.id.responderName);
            responderEmail = view.findViewById(R.id.responderEmail);
            responderUid = view.findViewById(R.id.responderUid);
            visitProfile = view.findViewById(R.id.visitResponderProfile);
            acceptButton = view.findViewById(R.id.acceptResponse);
            rejectButton = view.findViewById(R.id.rejectResponse);
            acceptButton.setOnClickListener(v -> {
                try {

                    //Marking that specific invitation response as accepted
                    FirebaseFirestore
                            .getInstance()
                            .collection("invitationresponses")
                            .whereEqualTo("invitationRef", response.getInvitationRef())
                            .whereEqualTo("responderRef", response.getResponderRef())
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        DocumentReference documentReference = document.getReference();
                                        documentReference.update("accepted", true);
                                    }
                                } else {
                                    System.out.println("Could Not Accept Response");
                                }
                            });

                    //Sending emails to everyone that accepted that invitation that got rejected
                    FirebaseFirestore
                            .getInstance()
                            .collection("invitationresponses")
                            .whereEqualTo("invitationRef", response.getInvitationRef())
                            .whereNotEqualTo("accepted", true)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    System.out.println("PLEASE");
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        DocumentReference documentReference = document.getReference();
                                        documentReference.get().addOnSuccessListener(task2 -> {
                                            User requester = task2.toObject(User.class);
                                            System.out.println("SENDING REJECTION EMAIL TO " + requester.getEmail());
                                            Mail mail1 = new Mail(requester.getEmail(), requester.getName(),
                                                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), "C");
                                            mail1.execute((Object) null);
                                        });



                                    }
                                } else {
                                    System.out.println("Could Not Send Rejection Response");
                                }
                            });

                    //Sending an "You matched with a roommate" email to both people on the accepted invitation response
                    FirebaseFirestore
                            .getInstance()
                            .document(String.valueOf(response.getInvitationRef().getPath()))
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot snap = task.getResult();
                                    String rName = responderName.getText().toString().substring(18);
                                    String rEmail = responderEmail.getText().toString().substring(19);

                                    Mail mail1 = new Mail(rEmail, rName,
                                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), "B");
                                    mail1.execute((Object) null);

                                    Mail mail2 = new Mail(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                                            rEmail, rName, "B");
                                    mail2.execute((Object) null);

                                    Double numberOfBeds;
                                    try {
                                        numberOfBeds = (Double) snap.get("numBeds");
                                    } catch (Exception e) {numberOfBeds = 1.;}

                                    System.out.println("Number of Beds That Were Available: " + numberOfBeds);
                                    // this was the last available slot
                                    if (numberOfBeds <= 1)  {
                                        snap.getReference().update("numBeds", 0);
                                        snap.getReference().update("available", false);
                                    } else {
                                        snap.getReference().update("numBeds", numberOfBeds - 1);
                                    }
                                    Intent intent = new Intent(acceptButton.getContext(), RoommateFeedActivity.class);
                                    Toast.makeText(acceptButton.getContext(), "Accepted Response! New Roommate",
                                            Toast.LENGTH_LONG).show();
                                    acceptButton.getContext().startActivity(intent);
                                }
                            });

                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Something went wrong accepting invitation");
                }
            });
            rejectButton.setOnClickListener(v -> {
                try {

                    FirebaseFirestore
                            .getInstance()
                            .collection("invitationresponses")
                            .whereEqualTo("invitationRef", response.getInvitationRef())
                            .whereEqualTo("responderRef", response.getResponderRef())
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        DocumentReference documentReference = document.getReference();
                                        documentReference.update("accepted", false);
                                    }
                                } else {
                                    System.out.println("Could Not Reject Response");
                                }
                                Intent intent = new Intent(acceptButton.getContext(), RoommateFeedActivity.class);
                                acceptButton.getContext().startActivity(intent);
                            });
                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.out.println("Something went wrong rejecting invitation");
                }
            });
            visitProfile.setOnClickListener(c -> {
                Intent intent = new Intent(con, ProfilePageActivity.class);
                intent.putExtra("Uid", response.getResponderRef().getId());
                con.startActivity(intent);


            });
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "Must Click Accept or Reject", Toast.LENGTH_SHORT).show();
        }
    }
}