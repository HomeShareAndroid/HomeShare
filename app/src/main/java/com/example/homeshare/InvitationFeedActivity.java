package com.example.homeshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.Model.Invitation;
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

public class InvitationFeedActivity extends AppCompatActivity {
    User user;
    private RecyclerView recyclerView;
    public static HashMap<Invitation, DocumentReference> invToRef = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitationfeed);

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
                System.out.println("We got a Document!");
                usr[0] = documentSnapshot.toObject(User.class);
                user = usr[0];
                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                Date date = new Date();

                List<Invitation> invites = new ArrayList<>();
                FirebaseFirestore.getInstance().collection("invitations")
                        .whereGreaterThan("deadline", new Timestamp(date))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        if (!document.get("posterUid").equals(user.getUid())) {
                                            Invitation i = document.toObject(Invitation.class);
                                            invites.add(i);
                                            invToRef.put(i, document.getReference());
                                        }
                                    }
                                    recyclerView.setAdapter(new CustomAdapter(invites));

                                } else {
                                    System.out.println("Getting Feed Not Successful");
                                }
                            }
                        });
                System.out.println("Got Feed For User: " + user.getUid());
                System.out.println("Feed is of Length: " + invites.size() );


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
