package com.example.homeshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.Model.Invitation;
import com.example.homeshare.Model.InvitationResponse;
import com.example.homeshare.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RoommateFeedActivity extends AppCompatActivity {
    User user;
    private RecyclerView recyclerView;
    public static HashMap<InvitationResponse, DocumentReference> responseToRef = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roommatefeed);

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser == null) {
            System.out.println("No User Logged in");
            Toast.makeText(this, "Not Logged In.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        final User[] usr = new User[1];
        try {
            DocumentReference documentReference = FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(fbUser.getUid());
            System.out.println(fbUser.getUid());

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                usr[0] = documentSnapshot.toObject(User.class);
                user = usr[0];
                RecyclerView recyclerView = findViewById(R.id.roommate_recycler_view);

                List<InvitationResponse> responses = new ArrayList<>();
                FirebaseFirestore.getInstance().collection("invitationresponses")
                        .whereEqualTo("posterRef", documentReference)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    InvitationResponse r = document.toObject(InvitationResponse.class);
                                    if (document.contains("accepted") && (boolean)document.get("accepted")) {
                                        responses.add(r);
                                        responseToRef.put(r, document.getReference());
                                    }
                                }
                                FirebaseFirestore.getInstance().collection("invitationresponses")
                                        .whereEqualTo("responderRef", documentReference)
                                        .get()
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                                    InvitationResponse r1 = document1.toObject(InvitationResponse.class);
                                                    if (document1.contains("accepted") && (boolean) document1.get("accepted")) {
                                                        responses.add(r1);
                                                        responseToRef.put(r1, document1.getReference());
                                                    }
                                                }
                                                recyclerView.setAdapter(new RoommateAdapter(getApplicationContext(), responses));
                                            } else {
                                                System.out.println("Getting Feed Not Successful");
                                            }
                                        });
                            } else {
                                System.out.println("Getting Feed Not Successful");
                            }
                        });
                System.out.println("Got Roommate Feed For User: " + user.getUid());
                System.out.println("Feed is of Length: " + responses.size() );


            });

        } catch (Exception e) {
            System.out.println("User Not Found from Firestore Query");

        }
        if (usr[0] == null) {
            System.out.println("We couldn't get the User");
        }
    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
