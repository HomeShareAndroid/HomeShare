package com.example.homeshare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.Model.Invitation;
import com.example.homeshare.Model.InvitationResponse;
import com.example.homeshare.Model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoommateAdapter extends RecyclerView.Adapter<RoommateAdapter.ViewHolder> {
    private List<InvitationResponse> data;
    private Map<ViewHolder, String> viewHolderToUID = new HashMap<>();
    private static Context con;
    public RoommateAdapter(Context con, List<InvitationResponse> data){
        this.con = con;
        this.data = data;
    }

    @Override
    public RoommateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.roommate_item_view, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(RoommateAdapter.ViewHolder holder, int position) {
        InvitationResponse response = data.get(position);
        holder.response = response;

        response.getInvitationRef().get().addOnSuccessListener(documentSnapshot -> {
            holder.address.setText("Property: " + documentSnapshot.toObject(Invitation.class).getAddress());
            response.getResponderRef().get().addOnSuccessListener(documentSnapshot1 -> {
                User responder = documentSnapshot1.toObject(User.class);
                holder.responderName.setText("Responder's Name: " +responder.getName());
                holder.responderUid.setText("Responder's Id: " + responder.getUid());
                holder.responderEmail.setText("Responder's Email: " + responder.getEmail());
                response.getPosterRef().get().addOnSuccessListener(documentSnapshot2 -> {
                    User poster = documentSnapshot2.toObject(User.class);
                    holder.posterEmail.setText("Poster's Email: " + poster.getEmail());
                    holder.posterUid.setText("Poster's Id: " + poster.getUid());
                    holder.posterName.setText("Poster's Name: " + poster.getName());
                });
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
        private TextView posterName;
        private TextView posterEmail;
        private TextView posterUid;
        private InvitationResponse response;
        private Button visitProfile;

        public ViewHolder(View view) {

            super(view);
            System.out.println("Creating Roommate Feed Item");
            address = view.findViewById(R.id.roommate_address);
            responderName = view.findViewById(R.id.responderRoommateName);
            responderEmail = view.findViewById(R.id.responderRoommateEmail);
            responderUid = view.findViewById(R.id.responderRoommateUid);
            posterName = view.findViewById(R.id.posterRoommateName);
            posterEmail = view.findViewById(R.id.posterRoommateEmail);
            posterUid = view.findViewById(R.id.posterRoommateUid);
            visitProfile = view.findViewById(R.id.visitResponderRoommateProfile);
            visitProfile.setOnClickListener(c -> {
                Intent intent = new Intent(con, ProfilePageActivity.class);
                intent.putExtra("Uid", response.getResponderRef().getId());
                con.startActivity(intent);
            });
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(con, ProfilePageActivity.class);
            intent.putExtra("Uid", response.getResponderRef().getId());
            con.startActivity(intent);
        }
    }
}